package com.zing.cloud.order.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamClient {

    String INPUT = "myMessage";

    String INPUT2 = "myMessage2";

//    @Input(StreamClient.INPUT)
//    SubscribableChannel input();

    @Output(StreamClient.INPUT)
    MessageChannel output();

//    @Input(StreamClient.INPUT2)
//    SubscribableChannel input2();

    @Output(StreamClient.INPUT2)
    MessageChannel output2();
}
