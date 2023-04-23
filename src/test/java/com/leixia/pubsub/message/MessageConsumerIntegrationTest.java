package com.leixia.pubsub.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leixia.pubsub.message.kafka.model.PriceMessage;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(properties = {"spring.kafka.listener.missing-topic-fatal=false"})
@EmbeddedKafka(
    bootstrapServersProperty = "spring.kafka.bootstrap-servers",
    partitions = 1,
    brokerProperties = {"log.dirs=./kafka-logs/"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageConsumerIntegrationTest {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  private BlockingQueue<ConsumerRecord<String, String>> messages = new LinkedBlockingQueue<>();

  @Test
  void shouldConsumeMessage() throws InterruptedException, JsonProcessingException {
    PriceMessage priceMessage = priceMessageFixture();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    kafkaTemplate.send("price-topic", objectMapper.writeValueAsString(priceMessage));

    ConsumerRecord<String, String> message = messages.poll(10, TimeUnit.SECONDS);
  }

  private PriceMessage priceMessageFixture() {
    PriceMessage priceMessage = new PriceMessage();
    priceMessage.setCurrency("USD");
    priceMessage.setLocalDateTime(LocalDateTime.now());
    priceMessage.setMcc("acc");
    priceMessage.setOffer(true);
    priceMessage.setPrice(10.10);
    priceMessage.setUuid(UUID.randomUUID());
    return priceMessage;
  }
}
