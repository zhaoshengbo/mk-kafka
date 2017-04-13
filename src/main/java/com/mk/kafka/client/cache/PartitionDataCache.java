package com.mk.kafka.client.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分区数据缓存.
 *
 * <p>
 * 存放{@link PartitionData}.
 *
 * @author zhaoshb
 * @see PartitionData
 */
public class PartitionDataCache {

	private static final Logger logger = LoggerFactory.getLogger(PartitionDataCache.class);

	private static List<PartitionData> partitionDataList = Collections.synchronizedList(new ArrayList<PartitionData>());

	public static void addPartitionData(PartitionData partitionData) {
		PartitionDataCache.partitionDataList.add(partitionData);
	}

	public static void shutDown() {
		for (PartitionData partitionData : PartitionDataCache.partitionDataList) {
			PartitionDataCache.shutDown(partitionData);
		}
	}

	private static void shutDown(PartitionData partitionData) {
		PartitionDataCache.logger.info("shutting down topic[{}] partition[{}]", partitionData.getTopic(), partitionData.getPartitionIndex());
		ExecutorService consumeService = partitionData.getConsumeService();
		consumeService.shutdown();
		try {
			boolean shutDownSuccess = consumeService.awaitTermination(5, TimeUnit.SECONDS);
			if (shutDownSuccess) {
				PartitionDataCache.logger.info("shut down topic[{}] partition[{}] completed", partitionData.getTopic(), partitionData.getPartitionIndex());
			} else {
				PartitionDataCache.logger.error("shut down topic[{}] partition[{}] timeout", partitionData.getTopic(), partitionData.getPartitionIndex());
			}
		} catch (InterruptedException e) {
			PartitionDataCache.logger.error("shut down topic[{}] partition[{}] error", partitionData.getTopic(), partitionData.getPartitionIndex(), e);
		}
	}

}
