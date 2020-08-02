package com.iakout.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName TimeInterceptor
 * @Description 自定义拦截器-处理时间的拦截器
 * @Date 2020/8/2 14:03
 * @Author iakout
 */
public class TimeInterceptor implements ProducerInterceptor<String,String>{
    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        //1、取出数据
        String value = producerRecord.value();
        //2、添加时间戳
        value = System.currentTimeMillis() + "--" + value;
        //3、构建新的ProducerRecord并返回
        return new ProducerRecord<String, String>(producerRecord.topic(),producerRecord.partition(),producerRecord.key(),value);
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }


}
