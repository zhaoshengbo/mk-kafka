package com.mk.kafka.client.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

/**
 * 消费者注解处理。
 * <p>
 * 当前版本并无实际用途.
 *
 * @author zhaoshb
 */
public class TopicProducerAnnotationParser implements IMethodAnnotationParser {

	public boolean isAcceptable(Annotation annotation) {
		return TopicProducerAnnotationParser.class.equals(annotation.getClass());
	}

	public void processObject(ApplicationContext context, Object bean, Method method, Annotation annotation) {
		// do nothing.
	}

}