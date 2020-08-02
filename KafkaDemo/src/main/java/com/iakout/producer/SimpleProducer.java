package com.iakout.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @ClassName SimpleProducer
 * @Description 简单生产者
 * @Date 2020/8/1 16:15
 * @Author iakout
 */
public class SimpleProducer {

    public static void main(String[] args) throws InterruptedException {
        //1、创建Kafka生产者配置信息
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.85.150:9092");
        //应答级别
        properties.put("acks","all");
        //重试次数
        properties.put("retries",3);
        //批次大小
        properties.put("batch.size",16384);
        //等待时间
        properties.put("linger.ms",1);
        //RecordAccumulator缓冲区大小
        properties.put("buffer.memory",33554432);
        //key、value的序列化类
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //2、创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        //3、发送数据
        for (int i = 0; i < 10 ; i++) {
            producer.send(new ProducerRecord<String, String>("mytopic","test","iakout--" +i ));
        }
        //4、关闭资源
        producer.close();
    }
}
