package com.brother.bysf.by.sf.web.mq.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sk-shifanwen
 * @date 2018/3/2
 */
@Component
public class TestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer.class);

    @KafkaListener(group = "${spring.kafka.consumer.group-id}",
            topics = "${by.sf.topic.test}",
            containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(List<ConsumerRecord<Bytes, Bytes>> consumerRecords, Acknowledgment ack) {
        for (ConsumerRecord record : consumerRecords) {
            LOGGER.info("receive msg:" + record);
        }
        ack.acknowledge();
    }
}
