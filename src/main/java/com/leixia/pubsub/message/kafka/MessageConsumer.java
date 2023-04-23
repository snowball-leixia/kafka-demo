package com.leixia.pubsub.message.kafka;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public MessageConsumer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = {"price-topic"})
  public void consume(ConsumerRecord<String, String> consumerRecord) {
    log.info("message received {}", consumerRecord.value());
    kafkaTemplate.send("outbound-price-topic", "bye");
  }
}
