package com.leixia.pubsub.message.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MessageSpec {

  @JsonProperty(value = "id")
  private UUID uuid;
  @JsonProperty(value = "topicName")
  private String topic;
  @JsonProperty(value = "createdAt")
  private LocalDateTime localDateTime;

}
