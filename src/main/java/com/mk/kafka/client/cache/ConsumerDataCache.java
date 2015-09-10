package com.mk.kafka.client.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者数据缓存，供关闭时使用.
 *
 * @author zhaoshb
 */
public class ConsumerDataCache {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerDataCache.class);

	private static List<ConsumerData> consumerDataList = new ArrayList<ConsumerData>();

	public static void putConsumerData(ConsumerData consumerData) {
		ConsumerDataCache.consumerDataList.add(consumerData);
	}

	public static void shutDown() {
		for (ConsumerData consumerData : ConsumerDataCache.consumerDataList) {
			ConsumerDataCache.shutDownConsumer(consumerData);
		}
	}

	private static void shutDownConsumer(ConsumerData consumerData) {
		ConsumerDataCache.logger.info("shuting down topic:{} consumer.", consumerData.getTopic());
		consumerData.getConsumerConnector().shutdown();
		ExecutorService executorService = consumerData.getExecutorService();
		executorService.shutdown();

		try {
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			ConsumerDataCache.logger.error("shut down topic:{} consumer error", consumerData.getTopic(), e);
		}
		ConsumerDataCache.consumerDataList = null;
		ConsumerDataCache.logger.info("shut down topic:{} consumer completed", consumerData.getTopic());
	}

}
