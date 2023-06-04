package com.leixia.pubsub.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topic")
@Data
public class KafkaTopicProperties {

  private String inbound;

  private String outbound;
}
