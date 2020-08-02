package com.iakout.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @ClassName CounterInterceptor
 * @Description 自定义拦截器-计数拦截器
 * @Date 2020/8/2 14:08
 * @Author iakout
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {
    int success;
    int error;

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e == null) {
            success++;
        } else {
            error++;
        }
    }

    @Override
    public void close() {
        System.out.println("success num: " + success);
        System.out.println("error num: " + error);
    }
}
