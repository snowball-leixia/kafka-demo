package com.leixia.pubsub.message.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
  private static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
  private final KafkaTemplate<String, String> kafkaTemplate;

  public MessageConsumer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = {"price-topic"})
  public void consume(ConsumerRecord<String, String> consumerRecord) {
    logger.info("message received {}", consumerRecord.value());
    kafkaTemplate.send("outbound-price-topic", "bye");
  }
}
