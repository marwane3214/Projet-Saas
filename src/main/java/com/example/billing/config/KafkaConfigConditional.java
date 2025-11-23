package com.example.billing.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaConfigConditional {
    // This class ensures Kafka configuration is only loaded when bootstrap-servers is set
}

