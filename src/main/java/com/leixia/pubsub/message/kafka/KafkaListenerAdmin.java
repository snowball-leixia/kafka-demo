package com.leixia.pubsub.message.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerAdmin {

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  @Setter
  @Getter
  private boolean isStartedManually = false;

  public KafkaListenerAdmin(
        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
    this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
  }

  public void startListener() {
    kafkaListenerEndpointRegistry.start();
    isStartedManually = true;
  }

  public void stopListener() {
    kafkaListenerEndpointRegistry.stop();
    isStartedManually = false;
  }

  public boolean isStarted() {
    return isStartedManually;
  }

}