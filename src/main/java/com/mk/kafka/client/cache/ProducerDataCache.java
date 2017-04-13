package com.mk.kafka.client.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生产者数据缓存.
 *
 * @author zhaoshb
 */
public class ProducerDataCache {

	private static Map<String, ProducerData> topicProducerMap = new ConcurrentHashMap<>();

	public static void put(String topic, ProducerData producerData) {
		ProducerDataCache.topicProducerMap.put(topic, producerData);
	}

	public static ProducerData get(String topic) {
		return ProducerDataCache.topicProducerMap.get(topic);
	}

	public static boolean contains(String topic) {
		return ProducerDataCache.topicProducerMap.containsKey(topic);
	}

	public static void shutdown() {
		for (ProducerData producerData : ProducerDataCache.topicProducerMap.values()) {
			producerData.getProducer().close();
		}
		ProducerDataCache.topicProducerMap = null;
	}

}
