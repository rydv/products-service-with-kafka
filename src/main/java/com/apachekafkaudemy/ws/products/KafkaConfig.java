package com.apachekafkaudemy.ws.products;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.apachekafkaudemy.ws.products.service.ProductCreatedEvent;

import java.util.HashMap;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.retries}")
    private int retries;

    @Value("${spring.kafka.producer.retry.backoff.ms}")
    private int retryBackoffMs;

    @Value("${spring.kafka.producer.delivery.timeout.ms}")
    private int deliveryTimeoutMs;

    @Value("${spring.kafka.producer.linger.ms}")
    private int lingerMs;

    @Value("${spring.kafka.producer.request.timeout.ms}")
    private int requestTimeoutMs;

    @Value("${spring.kafka.producer.min.insync.replicas}")
    private int minInsyncReplicas;

    @Value("${spring.kafka.producer.max.in.flight.requests.per.connection}")
    private int maxInFlightRequests;

    @Value("${spring.kafka.producer.enable-idempotence}")
    private boolean enableIdempotence;

    @Bean
    public NewTopic creaTopic() {
        return TopicBuilder.name("product-create-events-topic")
            .partitions(3)
            .replicas(3)
            .configs(Map.of("min.insync.replicas", String.valueOf(minInsyncReplicas)))
            .build();
    }

    @Bean
    public KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public DefaultKafkaProducerFactory<String, ProductCreatedEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        configProps.put(ProducerConfig.ACKS_CONFIG, acks);
        configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retryBackoffMs);
        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeoutMs);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeoutMs);
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequests);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
