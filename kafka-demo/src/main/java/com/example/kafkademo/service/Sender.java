package com.example.kafkademo.service;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    //@Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    Faker faker;

    public void send (String topic, String payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void generate () {
        System.out.println("Sending message");
        //faker = (Faker) Faker.instance();
        faker = new Faker();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
        final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));
        Flux.zip(interval, quotes)
                .map(it -> kafkaTemplate.send("hobbit", String.valueOf(faker.random().nextInt(42)), it.getT2())).blockLast();
    }
}
