package com.iakout.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * @ClassName CommitConsumer
 * @Description 手动提交consumer，自定义存储offset-可以存储在mysql等数据库中
 * @Date 2020/8/2 13:30
 * @Author iakout
 */
public class CommitConsumer {
    private static Map<TopicPartition, Long> currentOffset = new HashMap<>();

    public static void main(String[] args) {
        //1、创建消费者配置信息
        Properties properties = new Properties();
        //Kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.85.150:9092");
        //关闭自动提交-提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        //key的反序列化类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hello");
        //2、创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        //3、订阅主题
        List<String> topicList = new ArrayList<>();
        topicList.add("mytopic");

        consumer.subscribe(topicList, new ConsumerRebalanceListener() {
            //该方法在消费者重新分配分区之前调用
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                commitOffset(currentOffset);//异步提交
            }

            //该方法在消费者重新分配分区之后调用
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                currentOffset.clear();
                for (TopicPartition topicPartition : collection) {
                    consumer.seek(topicPartition, getOffset(topicPartition));
                }
            }
        });
        //等待时间为100毫秒
        Duration duration = Duration.ofMillis(100);

        //4、拉取数据
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(duration);
            //5、解析consumerRecords
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord.key() + "--" + consumerRecord.value());
                currentOffset.put(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()), consumerRecord.offset());
            }
            commitOffset(currentOffset);
        }
    }

    //自定义获取某分区offset的方法
    private static long getOffset(TopicPartition partition) {

        return 0;
    }

    //自定义提交分区offset的方法-可以将offset存储在数据库中 groupId|topic|partitionId|offset
    private static void commitOffset(Map<TopicPartition, Long> currentOffset) {

    }
}
