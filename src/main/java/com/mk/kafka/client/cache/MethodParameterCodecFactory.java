package com.mk.kafka.client.cache;

import kafka.serializer.Decoder;

/**
 * 方法参数编码解码工厂.
 *
 * @author zhaoshb
 */
class MethodParameterCodecFactory {

    private Decoder<?> decoder = null;

    MethodParameterCodecFactory() {
        super();
    }

    Decoder<?> getDecoder() {
        return this.decoder;
    }

    void setDecoder(Decoder<?> decoder) {
        this.decoder = decoder;
    }

}
