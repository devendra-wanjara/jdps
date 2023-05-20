package com.example.kafkademo.service;

import org.springframework.stereotype.Component;

@Component
public class SendService {

    private final Sender sender;

    public SendService (Sender sender) {
        this.sender = sender;
    }

    public void sendMsg () {
        sender.send("devTopic1", "Hello dEVEN Kafka");
    }
}
