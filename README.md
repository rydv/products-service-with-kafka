# products-service-with-kafka

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker

### Running the Service

1. Start Kafka using Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. Create Kafka Topic:
   ```bash
   docker exec -it kafka kafka-topics.sh \
   --create \
   --topic product-create-events-topic \
   --bootstrap-server localhost:9092 \
   --partitions 3 \
   --replication-factor 1
   ```

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Testing the Service

Send a POST request to create a product:
```bash
curl -X POST http://localhost:<assigned_port>/products \
-H "Content-Type: application/json" \
-d '{
    "title": "Test Product",
    "price": 99.99,
    "quantity": 10
}'
```

### Monitoring Kafka Messages

To consume messages from the topic:
```bash
docker exec -it kafka kafka-console-consumer.sh \
--topic product-create-events-topic \
--bootstrap-server localhost:9092 \
--from-beginning
```

### Stopping the Service

1. Stop the Spring Boot application (Ctrl+C)
2. Stop Kafka and related containers:
   ```bash
   docker-compose down
   ```

## Project Structure

- Uses Spring Boot 3.4.3
- Implements Kafka Producer for product creation events
- Dynamic port allocation (check console logs for assigned port)
- JSON serialization for Kafka messages

## Configuration

Key configurations can be found in:
- `application.properties`: Kafka and application settings
- `pom.xml`: Project dependencies and build configuration

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request