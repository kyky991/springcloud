package com.zing.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.zing.ad.mysql.dto.MySqlRowData;
import com.zing.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

/**
 * @author Zing
 * @date 2019-11-26
 */
@Slf4j
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    @KafkaListener(topics = "ad-search-mysql-data", groupId = "ad-search")
    public void process(ConsumerRecord<?, ?> record) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            MySqlRowData rowData = JSON.parseObject(msg.toString(), MySqlRowData.class);

            log.info("kafka msg: {}", JSON.toJSONString(rowData));
        }
    }
}
