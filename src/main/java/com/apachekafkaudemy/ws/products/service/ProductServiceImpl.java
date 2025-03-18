package com.apachekafkaudemy.ws.products.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.apachekafkaudemy.ws.products.rest.CreateProductRestModel;

@Service
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger LOGGER =  LoggerFactory.getLogger(this.getClass());


    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {

        // Placeholder for database entry logic
        // TODO: Implement database entry logic here
        
        // Generate a unique product ID
        String productId = UUID.randomUUID().toString();

        // Create a ProductCreatedEvent object
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, 
            productRestModel.getTitle(), 
            productRestModel.getPrice(), 
            productRestModel.getQuantity());

        // Publish the event to Kafka synchronously
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-create-events-topic", productId, productCreatedEvent).get();
        LOGGER.info("**** Product created with id: {}", productId);
        LOGGER.info("Message sent to topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("Message sent to partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Message offset: {}", result.getRecordMetadata().offset());
        LOGGER.info("Message timestamp: {}", result.getRecordMetadata().timestamp());

        return productId; // Return the generated product ID
    }
}
