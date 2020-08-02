package com.iakout.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName SimpleConsumer
 * @Description 简单消费者-自动提交
 * @Date 2020/8/2 12:21
 * @Author iakout
 */
public class SimpleConsumer {
    public static void main(String[] args) {
        //1、创建消费者配置信息
        Properties properties = new Properties();
        //Kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.85.150:9092");
        //开启自动提交-提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //自动提交延时
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //key的反序列化类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hello");
        //从头消费-前提是换组名以及之前的offset数据不存在
        //properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //2、创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //3、订阅主题
        List<String> topicList = new ArrayList<>();
        topicList.add("mytopic");
        consumer.subscribe(topicList);
        //等待时间为100毫秒
        Duration duration = Duration.ofMillis(100);

        //4、拉取数据
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(duration);
            //5、解析consumerRecords
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key() + "--" + consumerRecord.value());
            }
            //consumer.commitSync();//手动提交-同步提交
            //手动提交-异步提交
            /*consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {

                }
            });*/

        }
    }
}
