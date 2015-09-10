package com.mk.kafka.client.cache;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;

/**
 * 方法参数编码解码工厂.
 *
 *
 * @author zhaoshb
 */
public class MethodParameterCodecFactory {

	private Decoder<?> decoder = null;

	private Encoder<?> encoder = null;

	public MethodParameterCodecFactory() {
		super();
	}

	public MethodParameterCodecFactory(Encoder<?> encoder, Decoder<?> decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	public Decoder<?> getDecoder() {
		return this.decoder;
	}

	public void setDecoder(Decoder<?> decoder) {
		this.decoder = decoder;
	}

	public Encoder<?> getEncoder() {
		return this.encoder;
	}

	public void setEncoder(Encoder<?> encoder) {
		this.encoder = encoder;
	}

}
