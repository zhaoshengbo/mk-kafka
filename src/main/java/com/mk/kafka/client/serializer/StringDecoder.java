package com.mk.kafka.client.serializer;

import java.io.UnsupportedEncodingException;

import kafka.serializer.Decoder;

/**
 * 字符串解码.
 *
 * <p>
 * 替换kafka.serializer.StringDecoder.
 *
 * @author zhaoshb
 */
public class StringDecoder implements Decoder<String> {

	public String fromBytes(byte[] arg0) {
		try {
			return new String(arg0, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
