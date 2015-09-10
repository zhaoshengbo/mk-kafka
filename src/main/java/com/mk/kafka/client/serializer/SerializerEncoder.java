package com.mk.kafka.client.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * 序列化编码.
 *
 * <p>
 *
 * @author zhaoshb
 */
public class SerializerEncoder implements Encoder<Serializable> {

	public SerializerEncoder(VerifiableProperties verifiableProperties) {

	}

	public byte[] toBytes(Serializable object) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			try {
				bos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
