package com.mk.kafka.client.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * kafka配置参数，初始化连接时使用.
 *
 * <p>
 *
 * @author zhaoshb
 */
@Configuration
@PropertySource("classpath:kafka.properties")
public class KafkaConfig {

	private static KafkaConfig instance = null;

	/************************************* 消费者 ************************************/

	@Value("${zk.connect}")
	private String zkConnect = null;

	@Value("${zk.connectiontimeout.ms}")
	private String zkConnectionTimeoutMS = null;

	@Value("${zk.sessiontimeout.ms}")
	private String zkSessionTimeoutMS = null;

	@Value("${auto.offset.reset}")
	private String autoOffsetRest = null;

	@Value("${socket.receive.buffer.bytes}")
	private String socketReceiveBufferBytes = null;

	@Value("${fetch.message.max.bytes}")
	private String fethcMessageMaxBytes = null;

	@Value("${auto.commit.interval.ms}")
	private String autoCommitIntervalMS = null;

	/************************************* 生产者 ************************************/

	@Value("${queue.buffering.max.ms}")
	private String queueBufferingMaxMS = null;

	@Value("${topic.metadata.refresh.interval.ms}")
	private String topicMetadataRefreshIntervalMS = null;

	@Value("${queue.buffering.max.messages}")
	private String queueBufferingMaxMessages = null;

	@Value("${retry.backoff.ms}")
	private String retryBackOffMS = null;

	@Value("${message.send.max.retries}")
	private String messageSendMaxRetries = null;

	@Value("${send.buffer.bytes}")
	private String sendBufferBytes = null;

	@Value("${socket.request.max.bytes}")
	private String socketRequestMaxBytes = null;

	@Value("${socket.send.buffer.bytes}")
	private String socketSendBufferBytes = null;

	@Value("${request.required.acks}")
	private String requestRequiredAcks = null;

	/******************************************************************************/

	private Properties produceProperties = null;

	private Properties consumerProperties = null;

	public static KafkaConfig getIntance() {
		return KafkaConfig.instance;
	}

	@PostConstruct
	public void initialize() {
		this.initProducerProperties();
		this.initConsumerProperties();
		KafkaConfig.instance = this;
	}

	public String getZkConnect() {
		return this.zkConnect;
	}

	public void setZkConnect(String zkConnect) {
		this.zkConnect = zkConnect;
	}

	public String getZkConnectionTimeoutMS() {
		return this.zkConnectionTimeoutMS;
	}

	public String getZkSessionTimeoutMS() {
		return this.zkSessionTimeoutMS;
	}

	public String getAutoOffsetRest() {
		return this.autoOffsetRest;
	}

	public void setAutoOffsetRest(String autoOffsetRest) {
		this.autoOffsetRest = autoOffsetRest;
	}

	public String getSocketReceiveBufferBytes() {
		return this.socketReceiveBufferBytes;
	}

	public void setSocketReceiveBufferBytes(String socketReceiveBufferBytes) {
		this.socketReceiveBufferBytes = socketReceiveBufferBytes;
	}

	public String getFethcMessageMaxBytes() {
		return this.fethcMessageMaxBytes;
	}

	public void setFethcMessageMaxBytes(String fethcMessageMaxBytes) {
		this.fethcMessageMaxBytes = fethcMessageMaxBytes;
	}

	public String getAutoCommitIntervalMS() {
		return this.autoCommitIntervalMS;
	}

	public void setAutoCommitIntervalMS(String autoCommitIntervalMS) {
		this.autoCommitIntervalMS = autoCommitIntervalMS;
	}

	public String getQueueBufferingMaxMS() {
		return this.queueBufferingMaxMS;
	}

	public void setQueueBufferingMaxMS(String queueBufferingMaxMS) {
		this.queueBufferingMaxMS = queueBufferingMaxMS;
	}

	public String getTopicMetadataRefreshIntervalMS() {
		return this.topicMetadataRefreshIntervalMS;
	}

	public void setTopicMetadataRefreshIntervalMS(String topicMetadataRefreshIntervalMS) {
		this.topicMetadataRefreshIntervalMS = topicMetadataRefreshIntervalMS;
	}

	public String getQueueBufferingMaxMessages() {
		return this.queueBufferingMaxMessages;
	}

	public void setQueueBufferingMaxMessages(String queueBufferingMaxMessages) {
		this.queueBufferingMaxMessages = queueBufferingMaxMessages;
	}

	public String getRetryBackOffMS() {
		return this.retryBackOffMS;
	}

	public void setRetryBackOffMS(String retryBackOffMS) {
		this.retryBackOffMS = retryBackOffMS;
	}

	public String getMessageSendMaxRetries() {
		return this.messageSendMaxRetries;
	}

	public void setMessageSendMaxRetries(String messageSendMaxRetries) {
		this.messageSendMaxRetries = messageSendMaxRetries;
	}

	public String getSendBufferBytes() {
		return this.sendBufferBytes;
	}

	public void setSendBufferBytes(String sendBufferBytes) {
		this.sendBufferBytes = sendBufferBytes;
	}

	public String getSocketRequestMaxBytes() {
		return this.socketRequestMaxBytes;
	}

	public void setSocketRequestMaxBytes(String socketRequestMaxBytes) {
		this.socketRequestMaxBytes = socketRequestMaxBytes;
	}

	public String getSocketSendBufferBytes() {
		return this.socketSendBufferBytes;
	}

	public void setSocketSendBufferBytes(String socketSendBufferBytes) {
		this.socketSendBufferBytes = socketSendBufferBytes;
	}

	public String getRequestRequiredAcks() {
		return this.requestRequiredAcks;
	}

	public void setRequestRequiredAcks(String requestRequiredAcks) {
		this.requestRequiredAcks = requestRequiredAcks;
	}

	public void setProduceProperties(Properties produceProperties) {
		this.produceProperties = produceProperties;
	}

	public void setConsumerProperties(Properties consumerProperties) {
		this.consumerProperties = consumerProperties;
	}

	public Properties getProduceProperties() {
		return this.produceProperties;
	}

	public Properties getConsumerProperties() {
		return this.consumerProperties;
	}

	private void initProducerProperties() {
		Properties props = new Properties();
		props.put("queue.buffering.max.ms", this.getQueueBufferingMaxMS());
		props.put("topic.metadata.refresh.interval.ms", this.getTopicMetadataRefreshIntervalMS());
		props.put("queue.buffering.max.messages", this.getQueueBufferingMaxMessages());
		props.put("retry.backoff.ms", this.getRetryBackOffMS());
		props.put("message.send.max.retries", this.getMessageSendMaxRetries());
		props.put("send.buffer.bytes", this.getSendBufferBytes());
		props.put("socket.request.max.bytes", this.getSocketRequestMaxBytes());
		props.put("socket.receive.buffer.bytes", this.getSocketReceiveBufferBytes());
		props.put("socket.send.buffer.bytes", this.getSocketSendBufferBytes());
		props.put("request.required.acks", this.getRequestRequiredAcks());

		this.produceProperties = props;
	}

	private void initConsumerProperties() {
		Properties props = new Properties();
		props.put("zookeeper.connect", this.getZkConnect());
		props.put("zookeeper.session.timeout.ms", this.getZkSessionTimeoutMS());
		props.put("zookeeper.connect.timeout.ms", this.getZkConnectionTimeoutMS());
		props.put("auto.offset.reset", this.getAutoOffsetRest());
		props.put("socket.receive.buffer.bytes", this.getSocketReceiveBufferBytes());
		props.put("fetch.message.max.bytes", this.getFethcMessageMaxBytes());
		props.put("auto.commit.interval.ms", this.getAutoCommitIntervalMS());

		this.consumerProperties = props;
	}

}
