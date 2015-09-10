package com.mk.kafka.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mk.kafka.client.annotation.IMethodAnnotationParser;
import com.mk.kafka.client.annotation.TopicConsumerAnnotationParser;
import com.mk.kafka.client.annotation.TopicProducerAnnotationParser;
import com.mk.kafka.client.cache.ConsumerDataCache;
import com.mk.kafka.client.cache.PartitionDataCache;
import com.mk.kafka.client.cache.ProducerDataCache;
import com.mk.kafka.client.configuration.KafkaConfig;
import com.mk.kafka.client.stereotype.MkMessageService;
import com.mk.kafka.client.zk.ZkClientContainer;

import kafka.utils.ZKStringSerializer$;

/**
 * Kafka客户端上下文.
 *
 * <p>
 *
 *
 * @author zhaoshb
 */
public class MkKafkaContext implements DisposableBean, ApplicationContextAware {

	private List<IMethodAnnotationParser> methodAnnotationParserList = null;

	private ApplicationContext applicationContext = null;

	@PostConstruct
	public void initialize() {
		this.initContext();
		this.initZkClient();
		this.doPackageScan();
	}

	/**
	 * 关闭连接.
	 */
	public void destroy() throws Exception {
		ProducerDataCache.shutdown();
		ConsumerDataCache.shutDown();
		PartitionDataCache.shutDown();
		ZkClientContainer.close();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setMethodAnnotationParserList(List<IMethodAnnotationParser> methodAnnotationParserList) {
		this.methodAnnotationParserList = methodAnnotationParserList;
	}

	protected void initContext() {
		if (this.getMethodAnnotationParserList() == null) {
			this.registerDefaultMethodAnnotationParser();
		}
	}

	protected void initZkClient() {
		KafkaConfig kafkaConfig = this.getKafkaConfig();
		String zkServers = kafkaConfig.getZkConnect();
		Integer zkConnectTimtoutMS = Integer.valueOf(kafkaConfig.getZkConnectionTimeoutMS());
		Integer zkSessionTimeoutMS = Integer.valueOf(kafkaConfig.getZkSessionTimeoutMS());
		ZkClient zkClient = new ZkClient(zkServers, zkConnectTimtoutMS, zkSessionTimeoutMS, ZKStringSerializer$.MODULE$);
		ZkClientContainer.setZkClient(zkClient);
	}

	/**
	 * 注册默认的方法注解解析器.
	 *
	 * @see
	 * @see
	 */
	protected void registerDefaultMethodAnnotationParser() {
		this.methodAnnotationParserList = new ArrayList<IMethodAnnotationParser>();
		this.getMethodAnnotationParserList().add(new TopicConsumerAnnotationParser());
		this.getMethodAnnotationParserList().add(new TopicProducerAnnotationParser());
	}

	protected void doPackageScan() {
		Map<String, Object> beanMap = this.getApplicationContext().getBeansWithAnnotation(MkMessageService.class);
		for (Entry<String, Object> beanMapEntry : beanMap.entrySet()) {
			this.registerCandidate(beanMapEntry.getValue());
		}
	}

	protected void registerCandidate(Object object) {
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				this.registerCandidate(object, method, annotation);
			}
		}
	}

	protected void registerCandidate(Object bean, Method method, Annotation annotation) {
		for (IMethodAnnotationParser parser : this.getMethodAnnotationParserList()) {
			if (parser.isAcceptable(annotation)) {
				parser.processObject(this.getApplicationContext(), bean, method, annotation);
			}
		}
	}

	private List<IMethodAnnotationParser> getMethodAnnotationParserList() {
		return this.methodAnnotationParserList;
	}

	private KafkaConfig getKafkaConfig() {
		return KafkaConfig.getIntance();
	}

	private ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
