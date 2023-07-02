package com.leixia.pubsub.message.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.Lifecycle;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

  public void startListenersByGroupId(String groupId) {
    kafkaListenerEndpointRegistry
          .getAllListenerContainers()
          .stream()
          .filter(container -> Objects.equals(container.getGroupId(), groupId))
          .findFirst().ifPresent(Lifecycle::start);
    isStartedManually = true;
  }

  public void startListenersByListenerId(String listenerId) {
    kafkaListenerEndpointRegistry
          .getAllListenerContainers()
          .stream()
          .filter(container -> Objects.equals(container.getListenerId(), listenerId))
          .findFirst()
          .ifPresent(Lifecycle::stop);
    isStartedManually = true;
  }

  public void startListeners() {
    kafkaListenerEndpointRegistry.start();
    isStartedManually = true;
  }

  public void stopListeners() {
    kafkaListenerEndpointRegistry.stop();
    isStartedManually = false;
  }

  public boolean isStarted() {
    return isStartedManually;
  }

}