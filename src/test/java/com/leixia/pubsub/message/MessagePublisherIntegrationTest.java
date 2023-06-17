//package com.leixia.pubsub.message;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.leixia.pubsub.message.kafka.MessageConsumer;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.listener.MessageListener;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//
//@SpringBootTest(properties = {"spring.kafka.listener.missing-topic-fatal=false"})
//@EmbeddedKafka(
//    bootstrapServersProperty = "spring.kafka.bootstrap-servers",
//    partitions = 1,
//    brokerProperties = {"log.dirs=./kafka-logs/"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class MessagePublisherIntegrationTest {
//
//  @Autowired
//  private MessageConsumer messageConsumer;
//  @Autowired
//  private EmbeddedKafkaBroker embeddedKafkaBroker;
//  private BlockingQueue<ConsumerRecord<String, String>> messages;
//  private KafkaMessageListenerContainer<String, String> container;
//
//  @BeforeAll
//  void beforeAll() {
//    DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
//        getConsumerProperties());
//    ContainerProperties containerProperties = new ContainerProperties("outbound-price-topic");
//    container = new KafkaMessageListenerContainer<>(
//        consumerFactory, containerProperties);
//    messages = new LinkedBlockingQueue<>();
//    container.setupMessageListener((MessageListener<String, String>) messages::add);
//    container.start();
//    ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
//  }
//
//  private Map<String, Object> getConsumerProperties() {
//    return Map.of(
//        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
//        ConsumerConfig.GROUP_ID_CONFIG, "consumer",
//        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//  }
//
//  @Test
//  void whenConsumeMessageThenPublishToGivenTopic() throws InterruptedException {
//    ConsumerRecord<String, String> inboundMessage = new ConsumerRecord<>("price-topic",
//        1,
//        0L,
//        null,
//        "Hello");
//    messageConsumer.consume(inboundMessage);
//    ConsumerRecord<String, String> published = messages.poll(10, TimeUnit.SECONDS);
//    assertThat(Objects.requireNonNull(published).value()).isEqualTo("bye");
//  }
//
//  @AfterAll
//  void tearDown() {
//    container.stop();
//  }
//
//}
