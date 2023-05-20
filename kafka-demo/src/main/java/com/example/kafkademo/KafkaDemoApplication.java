package com.example.kafkademo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaDemoApplication {
    static ApplicationContext context;

    public static void main (String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner (ApplicationContext context) {
        return args -> {
            System.out.println("Let's get beans for two subclass which are invoked my their constructor:");
            var sender = (Sender) context.getBean(Sender.class);
            sender.send("devTopic1", "Hello dEVEN");
        };
    }
}
