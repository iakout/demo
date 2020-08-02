package com.iakout.partitioner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;

import java.util.Properties;

/**
 * @ClassName PartitionerProducer
 * @Description 生产者使用自定义分区器
 * @Date 2020/8/1 17:52
 * @Author iakout
 */
public class PartitionerProducer {
    public static void main(String[] args) {
        //1、创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.85.150:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //2、添加分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.iakout.partitioner.MyPartitioner");
        //3、创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        //4、发送数据
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("mytopic","test--" + i), (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println(recordMetadata.topic() + "--" + recordMetadata.partition() + "--" + recordMetadata.offset());
                }
            });
        }
        //4、关闭资源
        producer.close();

    }
}
