![JDBC](http://kafka.apache.org/images/kafka_logo.png)
![JDBC](http://www.imike.com/css/style/images/img/logo_w.png)

# 眯客kafka客户端封装
----------
## 相关注解 ##

 1. MkMessageService  标识类为kafka的消费类，用于类扫描时使用.
 2. MkTopicConsumer  标识方法为kafka主题消费方法，供自动扫描时发现.
 3. MkTopicProducer    标识方法为kafka主题生产者.
 
## 使用方法 ##
 
 4. 引入mave依赖.

 		<dependency>
			<groupId>com.mk</groupId>
			<artifactId>mk-kafka</artifactId>
			<version>1.0.1</version>
		</dependency>
	

 5.  将 kafka.properties文件拷到classpath根目录下，maven工程放到src/main/resources下.
 6. 配置MkKafkaContext,spring boot工程声明

		@Bean
		public MkKafkaContext getContext() {
			return new MkKafkaContext();
		}
	spring工程声明MkKafkaContext bean
	   <bean class="com.mk.kafka.client.MkKafkaContext"/>

 7. 配置切面
	 spring boot :
		 `@EnableAspectJAutoProxy(proxyTargetClass = true)`
  spring工程
		  ` < aop:aspectj-autoproxy proxy-target-class="true">
	    		< aop:include name="produceAsepct" />
		    < /aop:aspectj-autoproxy>
			< bean id ="produceAsepct" class="com.mk.kafka.client.aspect.MkProducerAnnotationAspect"/>`
 8. 消费类实现，创建新类，确保这个能被spring扫描到，并在类上加上注解@MkMessageService，并在消费方法配置注解@MkTopicConsumer.

	    @MkMessageService
	    public class TestConsumer {
    
	    	@Autowired
	    	private TestService testService = null;
    
	    	public TestConsumer() {
	    		this.getClass();
	    	}
    
		    @MkTopicConsumer(topic = "newTopic", group = "newTopicGroup", serializerClass = "com.mk.kafka.client.serializer.StringDecoder")
	    	public void consum(String data) {
		    	System.out.println(data);
	    	}
    
	    	@MkTopicConsumer(topic = "entityTopic", group = "newTopicGroup", serializerClass = "com.mk.kafka.client.serializer.SerializerDecoder")
	    	public void consumerTestEntity(TestEntity data) {
	    		this.getTestService().consumerData(data);
	    	}
    
	    	@MkTopicConsumer(topic = "newEntityTopic", group = "newTopicGroup", serializerClass = "com.mk.kafka.client.serializer.SerializerDecoder")
	    	public void consumerNewTestEntity(TestEntity data) {
	    		this.getTestService().consumerData(data);
	    	}
    
	    	private TestService getTestService() {
	    		return this.testService;
	    	}
    	}
 

 9. 生产者相关配置，创建新类并创建相关生产方法，在方法加上注解@MkTopicProducer并配置注解相关参数.

    	
	    	@MkTopicProducer(topic = "entityTopic", serializerClass = "com.mk.kafka.client.serializer.SerializerEncoder")
	    	public void sendObject(TestEntity value) {
	    	}
    
	    	@MkTopicProducer(topic = "newEntityTopic", serializerClass = "com.mk.kafka.client.serializer.SerializerEncoder", partitions = 2, replicationFactor = 2)
	    	public void sendNewbject(TestEntity value) {
	    		this.getClass();
	    	}
    
	    	public void consumerData(TestEntity testEntity) {
	    		System.out.println(testEntity);
	    	}


----------
###注解参数说明  
@MkTopicConsumer
| 参数名称                   | 相关说明                 |
|----------------------------|--------------------------|
| topic                      | 消费主题的名字           |
| group                      | 消费主题所属工作组的名字 |
| partitions                 | 主题分区数量             |
| failedRetryTimes           | 消费失败了重试次数       |
| partitonConsumeThreadCount | 分区消费线程数量         |
| serializerClass            | 序列化类                 |

@MkTopicProducer
| 参数名称          | 相关说明              |
|-------------------|-----------------------|
| topictopic        | 消费主题的名字        |
| partitions        | 分区数量              |
| replicationFactor | 分区备份数量          |
| serializerClass   | 数据序列化方法类      |
| partitionerClass  | 根据key进行分区方法类 |


#License
----------
Copyright (C) 2014-2015 WWW.IMIKE.COM

