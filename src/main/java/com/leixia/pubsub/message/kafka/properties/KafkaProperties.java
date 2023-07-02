package com.leixia.pubsub.message.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.listener")
public class KafkaProperties {

  @Getter
  @Setter
  private Enable price;

  @Getter
  @Setter
  private Enable order;

}
