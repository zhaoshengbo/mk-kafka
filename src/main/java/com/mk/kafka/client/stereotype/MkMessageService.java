package com.mk.kafka.client.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 标识类为kafka的消费类，用于类扫描时使用.
 *
 * <p>
 * 与{@link MkTopicConsumer}联合使用.
 *
 * @author zhaoshb
 * @see Component
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MkMessageService {
	/**
	 * 标识bean的名称.
	 *
	 * @return bean的名称.
	 */
	String value() default "";
}
