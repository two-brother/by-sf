package com.brother.bysf.by.sf.common.util;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.HashMap;
import java.util.Map;


/**
 * @author sk-shifanwen
 * @date 2018/5/21
 */
public class KafkaProducer {
    private static KafkaTemplate kafkaTemplate = new KafkaTemplate<>(producerFactory());;

    public static void send(String topic, byte[] key, byte[] data){
        ListenableFuture future = kafkaTemplate.send(topic, key, data);
        future.addCallback(new CallBackSuccess(),new FailCallBack(topic, key, data));
    }

    public static void send(String topic, String data){
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new CallBackSuccess(),new FailCallBack(topic,"".getBytes(), data.getBytes()));
    }

    private static void send(String topic, Integer parti, Long time, Object key, String value){
        kafkaTemplate.send(topic,parti,time,key,value);
    }

    /**
     *
     * Description：获取配置
     * Date：        2017年7月11日
     * @author      shaqf
     */
    private static Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        String list = ("kafka.host:9092");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, list);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return props;
    }

    /** 获取工厂 */
    private static ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 发送消息后的成功回调
     */
    static class CallBackSuccess implements SuccessCallback {
        @Override
        public void onSuccess(Object o) {
            System.out.println("SuccessCallback: " + o);
        }
    }

    /**
     * 发送消息后的失败回调
     */
    static class FailCallBack implements FailureCallback {
        String topic;
        String key;
        String data;

        FailCallBack(String topic, byte[] key, byte[] data){
            this.data = new String(data);
            this.key = new String(key);
            this.topic = topic;
        }
        @Override
        public void onFailure(Throwable throwable) {
            System.out.println("FailureCallback topic:" + topic + ", key:" + key + ",data:" + data);
            throwable.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception{
        String topic = "by-sf-test";
        String keyPrefix = "key-";
        String valuePrefix = "v-";
        for (int i=0 ; i< 50;i++){
            KafkaProducer.send(topic, (keyPrefix + i).getBytes(),(valuePrefix + i).getBytes());
        }
    }
}
