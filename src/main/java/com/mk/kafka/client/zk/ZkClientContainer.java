package com.mk.kafka.client.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * ZkClient容器.
 * <p>
 * 保持zk连接的目的是在生产者切面初始化的时候能够判断的主题是否存在</br>
 * 如果不存在则创建主题，存在则建立相应的producer.
 *
 * @author zhaoshb
 */
public class ZkClientContainer {

	private static ZkClient zkClient = null;

	public static void setZkClient(ZkClient zkClient) {
		ZkClientContainer.zkClient = zkClient;
	}

	public static ZkClient getZkClient() {
		return ZkClientContainer.zkClient;
	}

	public static void close() {
		ZkClientContainer.zkClient.close();
	}
}
