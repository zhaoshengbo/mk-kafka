package com.mk.kafka.client.cache;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;

public class MethodParameterCodcCache {

	private static Map<Method, MethodParameterCodecFactory> methodParamterCodecMap = null;

	static {
		MethodParameterCodcCache.methodParamterCodecMap = new ConcurrentHashMap<Method, MethodParameterCodecFactory>();
	}

	public static boolean constains(Method method) {
		return MethodParameterCodcCache.methodParamterCodecMap.containsKey(method);
	}

	public static Encoder<?> getEncoder(Method method) {
		if (MethodParameterCodcCache.methodParamterCodecMap.containsKey(method)) {
			return MethodParameterCodcCache.methodParamterCodecMap.get(method).getEncoder();
		}
		return null;
	}

	public static Decoder<?> getDecoder(Method method) {
		if (MethodParameterCodcCache.methodParamterCodecMap.containsKey(method)) {
			return MethodParameterCodcCache.methodParamterCodecMap.get(method).getDecoder();
		}
		return null;
	}

	public static void putEncoder(Method method, Encoder<?> encoder) {
		synchronized (method) {
			if (!MethodParameterCodcCache.methodParamterCodecMap.containsKey(method)) {
				MethodParameterCodecFactory factory = new MethodParameterCodecFactory();
				factory.setEncoder(encoder);
				MethodParameterCodcCache.methodParamterCodecMap.put(method, factory);
			} else {
				MethodParameterCodcCache.methodParamterCodecMap.get(method).setEncoder(encoder);
			}
		}
	}

	public static void putDecoder(Method method, Decoder<?> decoder) {
		synchronized (method) {
			if (!MethodParameterCodcCache.methodParamterCodecMap.containsKey(method)) {
				MethodParameterCodecFactory factory = new MethodParameterCodecFactory();
				factory.setDecoder(decoder);
				MethodParameterCodcCache.methodParamterCodecMap.put(method, factory);
			} else {
				MethodParameterCodcCache.methodParamterCodecMap.get(method).setDecoder(decoder);
			}
		}
	}
}
