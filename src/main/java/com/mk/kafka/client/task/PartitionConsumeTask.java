package com.mk.kafka.client.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.kafka.client.cache.MethodParameterCodcCache;

import kafka.serializer.Decoder;

/**
 * 分区消费任务.
 * <p>
 * 一个{@link TopicConsumeTask}对应多个{@link PartitionConsumeTask}.
 *
 * @author zhaoshb
 * @see TopicConsumeTask
 */
public class PartitionConsumeTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PartitionConsumeTask.class);

	private String topic = null;

	private Object object = null;

	private Method method = null;

	private byte[] message = null;

	public PartitionConsumeTask(Object object, Method method, byte[] message, String topic) {
		this.topic = topic;
		this.object = object;
		this.method = method;
		this.message = message;
	}

	public void run() {
		try {
			Decoder<?> decoder = MethodParameterCodcCache.getDecoder(this.getMethod());
			Object arg = decoder.fromBytes(this.getMessage());
			PartitionConsumeTask.logger.info("topic:{} consume data:{}", this.getTopic(), arg.toString());
			this.getMethod().invoke(this.getObject(), arg);
		} catch (IllegalAccessException e) {
			PartitionConsumeTask.logger.error(this.getCallMethodFailedMsg(), e);
		} catch (IllegalArgumentException e) {
			PartitionConsumeTask.logger.error(this.getCallMethodFailedMsg(), e);
		} catch (InvocationTargetException e) {
			PartitionConsumeTask.logger.error(this.getCallMethodFailedMsg(), e);
		}
	}

	private String getCallMethodFailedMsg() {
		return "call method " + this.getMethod().getName() + " failed.";
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

	private byte[] getMessage() {
		return this.message;
	}

}
