package com.mk.kafka.client.task;

import java.lang.reflect.Method;

import com.mk.kafka.client.cache.PartitionData;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * 主题消费任务.
 *
 * <p>
 * 对应多个{@link PartitionConsumeTask}.
 *
 * @author zhaoshb
 * @see PartitionConsumeTask
 */
public class TopicConsumeTask implements Runnable {

	private String topic = null;

	private Object object = null;

	private Method method = null;

	private KafkaStream<byte[], byte[]> stream = null;

	private PartitionData partitionData = null;

	public TopicConsumeTask(String topic, Object object, Method method, KafkaStream<byte[], byte[]> stream, PartitionData partitionData) {
		this.topic = topic;
		this.object = object;
		this.method = method;
		this.stream = stream;
		this.partitionData = partitionData;
	}

	public void run() {
		for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : this.getStream()) {
			byte[] message = messageAndMetadata.message();
			this.callMethod(message);
		}
	}

	private void callMethod(byte[] message) {
		this.getPartitionData().getConsumeService().execute(this.createPartitionConsumeTask(message));
	}

	private PartitionConsumeTask createPartitionConsumeTask(byte[] message) {
		return new PartitionConsumeTask(this.getObject(), this.getMethod(), message, this.getTopic());
	}

	private String getTopic() {
		return this.topic;
	}

	private Object getObject() {
		return this.object;
	}

	private Method getMethod() {
		return this.method;
	}

	private KafkaStream<byte[], byte[]> getStream() {
		return this.stream;
	}

	private PartitionData getPartitionData() {
		return this.partitionData;
	}

}
