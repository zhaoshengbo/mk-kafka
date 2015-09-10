package com.mk.kafka.client.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识方法为kafka主题生产者.
 *
 * <p>
 * 通过切面将返回值发送到特定主题,与{@link MkMessageService}联合使用.
 *
 * @author zhaoshb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MkTopicProducer {

	/**
	 * 消息发送的主题名称.
	 *
	 * @return 主题名称.
	 */
	String topic();

	/**
	 * 分区数量.
	 *
	 * @return 分区数量.
	 */
	int partitions() default 1;

	/**
	 * 分区备份数量.
	 *
	 * @return 备份数量.
	 */
	int replicationFactor() default 2;

	/**
	 * 数据序列化方法类.
	 *
	 * @return 全路径.
	 */
	String serializerClass() default "com.mk.kafka.client.serializer.StringEncoder";

	/**
	 * 根据key进行分区方法类.
	 *
	 * @return 全路径.
	 */
	String partitionerClass() default "com.mk.kafka.client.serializer.DefaultPartitioner";

}
