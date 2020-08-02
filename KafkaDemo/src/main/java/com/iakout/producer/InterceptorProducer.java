package com.iakout.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @ClassName InterceptorProducer
 * @Description 带自定义拦截器的生产者
 * @Date 2020/8/2 14:12
 * @Author iakout
 */
public class InterceptorProducer {
    public static void main(String[] args) {
        //1、创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.85.150:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        //2、添加拦截器
        ArrayList<String> interceptors = new ArrayList<>();
        interceptors.add("com.iakout.interceptor.CounterInterceptor");
        interceptors.add("com.iakout.interceptor.TimeInterceptor");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,interceptors);

        //3、创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        //4、发送数据
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("mytopic", "hello","iakout--" + i));

        }
        //4、关闭资源
        producer.close();

    }
}
