package com.mk.kafka.client.cache;

import java.util.concurrent.ExecutorService;

/**
 * 分区数据.
 * <p>
 * 分区索引与对应的消费线程池,与{@link PartitionDataCache}想对应.
 *
 * @author zhaoshb
 * @see PartitionDataCache
 */
public class PartitionData {

	private String topic = null;

	private int partitionIndex = -1;

	private ExecutorService consumeService = null;

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getPartitionIndex() {
		return this.partitionIndex;
	}

	public void setPartitionIndex(int partitionIndex) {
		this.partitionIndex = partitionIndex;
	}

	public ExecutorService getConsumeService() {
		return this.consumeService;
	}

	public void setConsumeService(ExecutorService consumeService) {
		this.consumeService = consumeService;
	}

}
