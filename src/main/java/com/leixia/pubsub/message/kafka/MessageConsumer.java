package com.leixia.pubsub.message.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
  private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
  private final KafkaTemplate<String, String> kafkaTemplate;


  public MessageConsumer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = {"price-topic"}, autoStartup = "false", concurrency = "5", groupId = "price-group")
  public void consumePrice(ConsumerRecord<String, String> consumerRecord) {
    logger.info("Price message received {}", consumerRecord.value());
    kafkaTemplate.send("outbound-price-topic", "bye");
  }

  @KafkaListener(topics = "order-topic", autoStartup = "false", concurrency = "5", groupId = "order-group")
  public void consumeOrder(ConsumerRecord<String, String> consumerRecord) {
    logger.info("Order message received {}", consumerRecord.value());
    kafkaTemplate.send("outbound-order-topic", "hello");
  }
}
