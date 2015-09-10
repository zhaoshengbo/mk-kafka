package com.mk.kafka.client.cache;

import kafka.javaapi.producer.Producer;

/**
 * 生产者相关数据，topic，producer.
 *
 * @author zhaoshb
 */
public class ProducerData {

	private String topic = null;

	private Producer<Object, Object> producer = null;

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Producer<Object, Object> getProducer() {
		return this.producer;
	}

	public void setProducer(Producer<Object, Object> producer) {
		this.producer = producer;
	}
}
