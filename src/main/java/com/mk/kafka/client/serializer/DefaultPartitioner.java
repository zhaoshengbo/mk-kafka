package com.mk.kafka.client.serializer;

import kafka.producer.Partitioner;
import kafka.utils.Utils;
import kafka.utils.VerifiableProperties;

/**
 * 默认分区实现算法.
 *
 * <p>
 * key的hashcode%分区数量.</br>
 * 替换kafka.producer.DefaultPartitioner默认实现.
 *
 * @author zhaoshb
 */
public class DefaultPartitioner implements Partitioner {

	public DefaultPartitioner(VerifiableProperties verifiableProperties) {

	}

	public int partition(Object key, int numPartitions) {
		return Utils.abs(key.hashCode()) % numPartitions;
	}

}
