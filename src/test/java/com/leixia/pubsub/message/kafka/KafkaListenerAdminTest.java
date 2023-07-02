package com.leixia.pubsub.message.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class KafkaListenerAdminTest {
  @Test
  void isStarted_shouldReturnManualStartUpStatus() {
    KafkaListenerEndpointRegistry registry = mock(KafkaListenerEndpointRegistry.class);
    KafkaListenerAdmin kafkaListenerAdmin = new KafkaListenerAdmin(registry);
    assertThat(kafkaListenerAdmin.isStarted()).isFalse();
  }

  @Test
  void startListener_shouldInvokeStartListener() {
    KafkaListenerEndpointRegistry registry = mock(KafkaListenerEndpointRegistry.class);
    KafkaListenerAdmin kafkaListenerAdmin = new KafkaListenerAdmin(registry);

    kafkaListenerAdmin.startListeners();

    verify(registry).start();
    assertThat(kafkaListenerAdmin.isStarted()).isTrue();
  }


  @Test
  void stopListener_shouldInvokeStartListener() {
    KafkaListenerEndpointRegistry registry = mock(KafkaListenerEndpointRegistry.class);
    KafkaListenerAdmin kafkaListenerAdmin = new KafkaListenerAdmin(registry);

    kafkaListenerAdmin.stopListeners();

    verify(registry).stop();
    assertThat(kafkaListenerAdmin.isStarted()).isFalse();
  }
}