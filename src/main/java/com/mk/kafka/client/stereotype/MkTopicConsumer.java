package com.mk.kafka.client.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识方法为kafka主题消费方法，供自动扫描时发现.
 *
 * <p>
 * 与{@link MkMessageService}联合使用.
 *
 * @author zhaoshb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MkTopicConsumer {

	/**
	 * 消费主题的名字.
	 *
	 * @return 主题名字.
	 */
	String topic();

	/**
	 * 消费主题所属工作组的名字.
	 *
	 * @return 工作组名称.
	 */
	String group();

	/**
	 * 主题分区数量.
	 *
	 * @return 分区数量.
	 */
	int partitions() default 1;

	/**
	 * 消费失败了重试次数.
	 *
	 * @return 重试次数.
	 */
	int failedRetryTimes() default 1;

	/**
	 * 分区消费线程数量.
	 *
	 * @return
	 */
	int partitonConsumeThreadCount() default 1;

	/**
	 * 序列化类.
	 *
	 * @return 类全路径.
	 */
	String serializerClass() default "com.mk.kafka.client.serializer.StringDecoder";

}
