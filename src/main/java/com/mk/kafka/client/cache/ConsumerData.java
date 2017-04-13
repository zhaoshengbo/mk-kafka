package com.mk.kafka.client.cache;

import java.util.concurrent.ExecutorService;

import kafka.javaapi.consumer.ConsumerConnector;

/**
 * 消费者数据，包含连接，线程池，主题名称.
 *
 * @author zhaoshb
 */
public class ConsumerData {

    private String topic = null;

    private int partitionConsumeThreadCount = 1;

    private ConsumerConnector consumerConnector = null;

    private ExecutorService executorService = null;

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPartitionConsumeThreadCount() {
        return this.partitionConsumeThreadCount;
    }

    public void setPartitionConsumeThreadCount(int partitionConsumeThreadCount) {
        this.partitionConsumeThreadCount = partitionConsumeThreadCount;
    }

    ConsumerConnector getConsumerConnector() {
        return this.consumerConnector;
    }

    public void setConsumerConnector(ConsumerConnector consumerConnector) {
        this.consumerConnector = consumerConnector;
    }

    ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

}
