package com.leixia.pubsub.message.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leixia.pubsub.message.kafka.model.PriceMessage;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public MessagePublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public String publish() throws JsonProcessingException {
    PriceMessage priceMessage = new PriceMessage();
    kafkaTemplate.send("outbound-price-topic", objectMapper.writeValueAsString(priceMessage));
    return UUID.randomUUID().toString();
  }
}
