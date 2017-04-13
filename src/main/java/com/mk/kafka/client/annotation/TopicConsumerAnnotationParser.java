package com.mk.kafka.client.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;

import com.mk.kafka.client.cache.ConsumerData;
import com.mk.kafka.client.cache.ConsumerDataCache;
import com.mk.kafka.client.cache.MethodParameterCodecCache;
import com.mk.kafka.client.cache.PartitionData;
import com.mk.kafka.client.cache.PartitionDataCache;
import com.mk.kafka.client.configuration.KafkaConfig;
import com.mk.kafka.client.stereotype.MkTopicConsumer;
import com.mk.kafka.client.task.TopicConsumeTask;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.Decoder;

/**
 * 主题消费者注解转换器.
 *
 * <p>
 *
 * @author zhaoshb
 */
public class TopicConsumerAnnotationParser implements IMethodAnnotationParser {

	/**
	 * 接收注解类型{@link MkTopicConsumer}
	 */
	public boolean isAcceptable(Annotation annotation) {
		return MkTopicConsumer.class.equals(annotation.annotationType());
	}

	/**
	 * 处理消费者.
	 * <P>
	 */
	public void processObject(ApplicationContext context, Object bean, Method method, Annotation annotation) {
		MkTopicConsumer consumerAnnotation = (MkTopicConsumer) annotation;
		this.putDecoder(method, consumerAnnotation);

		KafkaConfig config = context.getBean(KafkaConfig.class);
		Properties consumerProperties = this.getConsumerProperties(config);
		consumerProperties.put("group.id", consumerAnnotation.group());

		this.startConsumeData(bean, method, consumerAnnotation, consumerProperties);
	}

	private void startConsumeData(Object bean, Method method, MkTopicConsumer consumerAnnotation, Properties consumerProperties) {
		ConsumerConfig consumerConfig = new ConsumerConfig(consumerProperties);
		ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<>();
		topicCountMap.put(consumerAnnotation.topic(), consumerAnnotation.partitions());
		Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamMap = consumerConnector.createMessageStreams(topicCountMap);

		ConsumerData consumerData = new ConsumerData();
		consumerData.setTopic(consumerAnnotation.topic());
		consumerData.setConsumerConnector(consumerConnector);
		consumerData.setPartitionConsumeThreadCount(consumerAnnotation.partitonConsumeThreadCount());

		this.submitConsumerTask(bean, method, topicStreamMap, consumerData);

		ConsumerDataCache.putConsumerData(consumerData);
	}

	private void submitConsumerTask(Object bean, Method method, Map<String, List<KafkaStream<byte[], byte[]>>> topicStreamMap, ConsumerData consumerData) {
		List<KafkaStream<byte[], byte[]>> streamList = topicStreamMap.values().iterator().next();
		ExecutorService executorService = Executors.newFixedThreadPool(streamList.size());
		int partitionIndex = 0;
		for (KafkaStream<byte[], byte[]> stream : streamList) {
			PartitionData partitionData = this.createPartitonDataAndCache(partitionIndex++, consumerData);
			executorService.submit(new TopicConsumeTask(consumerData.getTopic(), bean, method, stream, partitionData));
		}
		consumerData.setExecutorService(executorService);
	}

	private PartitionData createPartitonDataAndCache(int partitionIndex, ConsumerData consumerData) {
		PartitionData partitionData = new PartitionData();
		partitionData.setPartitionIndex(partitionIndex);
		partitionData.setTopic(consumerData.getTopic());

		int partitionConsumeThreadCount = consumerData.getPartitionConsumeThreadCount();
		ExecutorService consumeService = Executors.newFixedThreadPool(partitionConsumeThreadCount);

		partitionData.setConsumeService(consumeService);
		// put into cache.
		PartitionDataCache.addPartitionData(partitionData);

		return partitionData;
	}

	private Properties getConsumerProperties(KafkaConfig config) {
		return (Properties) config.getConsumerProperties().clone();
	}

	private void putDecoder(Method method, MkTopicConsumer consumerAnnotation) {
		Decoder<?> decoder = this.getDecoderInstance(consumerAnnotation);
		MethodParameterCodecCache.putDecoder(method, decoder);
	}

	private Decoder<?> getDecoderInstance(MkTopicConsumer consumerAnnotation) {
		String decoderClassName = consumerAnnotation.serializerClass();
		try {
			return (Decoder<?>) Class.forName(decoderClassName).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
