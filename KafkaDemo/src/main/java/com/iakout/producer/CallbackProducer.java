package com.iakout.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @ClassName CallbackProducer
 * @Description 带回调函数的生产者
 * @Date 2020/8/1 16:16
 * @Author iakout
 */
public class CallbackProducer {
    public static void main(String[] args) {
        //1、创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.85.150:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        //2、创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        //3、发送数据
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        list.add("b");
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("mytopic", list.get(i%3),"iakout--" + i), (recordMetadata, e) -> {
                if(e==null){
                    System.out.println(recordMetadata.topic() + "--" + recordMetadata.partition() + "--" + recordMetadata.offset());
                }
            });
        }
        //4、关闭资源
        producer.close();

    }
}
