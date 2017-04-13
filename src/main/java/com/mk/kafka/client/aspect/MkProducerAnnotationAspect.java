package com.mk.kafka.client.aspect;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.mk.kafka.client.cache.ProducerData;
import com.mk.kafka.client.cache.ProducerDataCache;
import com.mk.kafka.client.configuration.KafkaConfig;
import com.mk.kafka.client.stereotype.MkTopicProducer;
import com.mk.kafka.client.zk.ZkClientContainer;

import kafka.admin.AdminUtils;
import kafka.cluster.Broker;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.utils.ZkUtils;
import scala.collection.Seq;

/**
 * 生产者切面.
 * <p>
 * <p>
 *
 * @author zhaoshb
 */
@Aspect
public class MkProducerAnnotationAspect {

    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @Around("anyPublicMethod() && @annotation(topicProducer) && args(value)")
    public Object aroundProduce(ProceedingJoinPoint pjp, MkTopicProducer topicProducer, Object value) throws Throwable {
        String topic = topicProducer.topic();
        String hashCodeString = Integer.toString(value.hashCode());
        KeyedMessage<Object, Object> message = new KeyedMessage<Object, Object>(topic, hashCodeString, value);
        Producer<Object, Object> producer = this.getProducer(topicProducer);
        producer.send(message);

        return pjp.proceed(new Object[]{value});
    }

    private Producer<Object, Object> getProducer(MkTopicProducer topicProducer) {
        return this.getProducerData(topicProducer).getProducer();
    }

    private ProducerData getProducerData(MkTopicProducer topicProducer) {
        String topic = topicProducer.topic();
        if (ProducerDataCache.contains(topic)) {
            return ProducerDataCache.get(topic);
        }
        synchronized (MkTopicProducer.class) {
            if (ProducerDataCache.contains(topic)) {
                return ProducerDataCache.get(topic);
            }
            return this.connect(topicProducer);
        }
    }

    private ProducerData connect(MkTopicProducer topicProducer) {
        String topic = topicProducer.topic();
        if (!this.isTopicExist(topic)) {
            this.createTopic(topicProducer);
        }
        Properties producerProperties = KafkaConfig.getIntance().getProduceProperties();
        producerProperties.put("serializer.class", topicProducer.serializerClass());
        producerProperties.put("partitioner.class", topicProducer.partitionerClass());
        producerProperties.put("metadata.broker.list", this.getBrokerHosts());

        ProducerConfig config = new ProducerConfig(producerProperties);
        Producer<Object, Object> producer = new Producer<>(config);

        ProducerData producerData = new ProducerData();
        producerData.setTopic(topic);
        producerData.setProducer(producer);

        // put into cache.
        ProducerDataCache.put(topic, producerData);

        return producerData;
    }

    private String getBrokerHosts() {
        StringBuilder brokerHosts = new StringBuilder();
        Seq<Broker> brokerList = ZkUtils.getAllBrokersInCluster(ZkClientContainer.getZkClient());
        scala.collection.Iterator<Broker> brokerIterator = brokerList.iterator();
        while (brokerIterator.hasNext()) {
            Broker broker = brokerIterator.next();
            brokerHosts.append(this.getBrokerHost(broker));
            brokerHosts.append(",");
        }
        return brokerHosts.substring(0, brokerHosts.length() - 1);
    }

    private String getBrokerHost(Broker broker) {
        return broker.host() + ":" + broker.port();
    }

    private void createTopic(MkTopicProducer topicProducer) {
        String topic = topicProducer.topic();
        int partitions = topicProducer.partitions();
        int replicationFactor = topicProducer.replicationFactor();
        ZkClient zkClient = ZkClientContainer.getZkClient();
        AdminUtils.createTopic(zkClient, topic, partitions, replicationFactor, new Properties());
    }

    private boolean isTopicExist(String topic) {
        String topicPath = ZkUtils.getTopicPath(topic);

        return ZkClientContainer.getZkClient().exists(topicPath);
    }

}
