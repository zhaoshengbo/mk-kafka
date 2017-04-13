package com.mk.kafka.client.cache;

import kafka.serializer.Decoder;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodParameterCodecCache {

    private static Map<Method, MethodParameterCodecFactory> methodParameterCodecMap = null;

    static {
        MethodParameterCodecCache.methodParameterCodecMap = new ConcurrentHashMap<>();
    }

    public static Decoder<?> getDecoder(Method method) {
        if (MethodParameterCodecCache.methodParameterCodecMap.containsKey(method)) {
            return MethodParameterCodecCache.methodParameterCodecMap.get(method).getDecoder();
        }
        return null;
    }

    public static void putDecoder(Method method, Decoder<?> decoder) {
        if (!MethodParameterCodecCache.methodParameterCodecMap.containsKey(method)) {
            MethodParameterCodecFactory factory = new MethodParameterCodecFactory();
            factory.setDecoder(decoder);
            MethodParameterCodecCache.methodParameterCodecMap.put(method, factory);
        } else {
            MethodParameterCodecCache.methodParameterCodecMap.get(method).setDecoder(decoder);
        }
    }
}
