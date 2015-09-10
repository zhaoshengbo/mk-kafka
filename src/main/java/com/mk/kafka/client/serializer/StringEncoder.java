package com.mk.kafka.client.serializer;

import java.io.UnsupportedEncodingException;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * 字符串编码.
 *
 * <p>
 * 替换kafka.serializer.StringEncoder.
 *
 * @author zhaoshb
 */
public class StringEncoder implements Encoder<String> {

	public StringEncoder(VerifiableProperties verifiableProperties) {
	}

	public byte[] toBytes(String arg0) {
		if (arg0 == null) {
			return null;
		}
		try {
			return arg0.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
