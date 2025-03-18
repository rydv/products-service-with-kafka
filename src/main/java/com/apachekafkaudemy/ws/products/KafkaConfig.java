package com.apachekafkaudemy.ws.products;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic creaTopic(){
        return TopicBuilder.name("product-create-events-topic")
            .partitions(3)
            .replicas(3)
            .configs(Map.of("min.insync.replicas","2"))
            .build();
    }
}
