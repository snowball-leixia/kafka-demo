package com.leixia.pubsub.message.kafka;


import com.leixia.pubsub.message.config.KafkaTopicProperties;
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
  private final KafkaTopicProperties topicProperties;
  private final String ip = "127.0.0.1";

  public MessageConsumer(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicProperties properties) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicProperties = properties;
  }

  @KafkaListener(topics = {"price-topic"})
  public void consume(ConsumerRecord<String, String> consumerRecord) {
    logger.info("message received {}", consumerRecord.value());
    kafkaTemplate.send("outbound-price-topic", "bye");
  }
}
