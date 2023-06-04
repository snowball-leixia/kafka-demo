//package com.leixia.pubsub.message;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.verify;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.leixia.pubsub.message.kafka.MessageConsumer;
//import com.leixia.pubsub.message.kafka.model.PriceMessage;
//import java.time.LocalDateTime;
//import java.util.UUID;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.ArgumentCaptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.util.ReflectionTestUtils;
//
//@SpringBootTest(properties = {"spring.kafka.listener.missing-topic-fatal=false"})
//@EmbeddedKafka(
//    bootstrapServersProperty = "spring.kafka.bootstrap-servers",
//    partitions = 1,
//    brokerProperties = {"log.dirs=./kafka-logs/"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class MessageConsumerIntegrationTest {
//
//  @Autowired
//  private KafkaTemplate<String, String> kafkaTemplate;
//  @Autowired
//  private MessageConsumer messageConsumer;
//
//  @Autowired
//  private ObjectMapper objectMapper;
//
//  private final BlockingQueue<ConsumerRecord<String, String>> messages = new LinkedBlockingQueue<>();
//
//  @Test
//  void shouldConsumeMessage() throws InterruptedException, JsonProcessingException {
//    Logger spiedLogger = spy(LoggerFactory.getLogger(MessageConsumer.class));
//    ReflectionTestUtils.setField(messageConsumer, "logger", spiedLogger);
//
//    PriceMessage priceMessage = priceMessageFixture();
//
//    objectMapper.registerModule(new JavaTimeModule());
//    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//    kafkaTemplate.send("price-topic", objectMapper.writeValueAsString(priceMessage));
//    messages.poll(10, TimeUnit.SECONDS);
//
//    ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
//
//    verify(spiedLogger).info(logMessageCaptor.capture());
//
//    assertThat(logMessageCaptor.getValue()).isNotBlank();
//  }
//
//  private PriceMessage priceMessageFixture() {
//    PriceMessage priceMessage = new PriceMessage();
//    priceMessage.setCurrency("USD");
//    priceMessage.setLocalDateTime(LocalDateTime.now());
//    priceMessage.setMcc("acc");
//    priceMessage.setOffer(true);
//    priceMessage.setPrice(10.10);
//    priceMessage.setUuid(UUID.randomUUID());
//    return priceMessage;
//  }
//}
