package com.zing.coupon.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author Zing
 * @date 2020-05-07
 */
public interface IKafkaService {

    /**
     * 消费优惠券 Kafka 消息
     *
     * @param record {@link ConsumerRecord}
     */
    void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record);

}
