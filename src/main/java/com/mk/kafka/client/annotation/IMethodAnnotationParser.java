package com.mk.kafka.client.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

/**
 * 注解转换器.
 *
 * <p>
 * 解析自定义注解.
 *
 * @author zhaoshb
 */
public interface IMethodAnnotationParser {

	/**
	 * 方法是否可以被处理.
	 *
	 * @param method
	 *            方法.
	 * @return 是否可处理.
	 */
	public boolean isAcceptable(Annotation annotation);

	/**
	 * 根据方法注解类型进行对象处理.
	 *
	 * @param context
	 *            应用上下文.
	 * @param bean
	 *            被处理bean.
	 * @param method
	 *            相关方法.
	 * @param annotation
	 *            相关注解.
	 */
	public void processObject(ApplicationContext context, Object bean, Method method, Annotation annotation);

}
