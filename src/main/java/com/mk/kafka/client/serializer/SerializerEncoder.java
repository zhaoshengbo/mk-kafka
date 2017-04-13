package com.mk.kafka.client.serializer;

import kafka.serializer.Encoder;

import java.io.*;

/**
 * 序列化编码.
 * <p>
 * <p>
 *
 * @author zhaoshb
 */
public class SerializerEncoder implements Encoder<Serializable> {

    public SerializerEncoder() {
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
            }
        }
    }

}
