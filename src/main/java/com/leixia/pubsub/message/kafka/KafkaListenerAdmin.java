package com.leixia.pubsub.message.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerAdmin {

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final String groupId;

  @Setter
  @Getter
  private boolean isRunning = false;

  public KafkaListenerAdmin(
        KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
        @Value("${kafka.group.id}") String groupId) {
    this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    this.groupId = groupId;
  }

  public void startListener() {
    MessageListenerContainer listenerContainer = getMessageListenerContainer();
    if (listenerContainer != null && !listenerContainer.isRunning()) {
      listenerContainer.start();
    }
  }

  public void stopListener() {
    MessageListenerContainer listenerContainer = getMessageListenerContainer();
    if (listenerContainer != null && listenerContainer.isRunning()) {
      listenerContainer.setAutoStartup(false);
      listenerContainer.stop();
    }
  }

  private MessageListenerContainer getMessageListenerContainer() {
    return kafkaListenerEndpointRegistry.getListenerContainer(this.groupId);
  }

  public boolean isContainerRunning() {
    return isRunning;
  }

}
