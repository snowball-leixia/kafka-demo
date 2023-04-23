package com.leixia.pubsub.message.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PriceMessage extends MessageSpec {

  @JsonProperty(value = "price")
  private Double price;
  @JsonProperty(value = "isOffer")
  private boolean isOffer;
  @JsonProperty(value = "mcc")
  private String mcc;
  @JsonProperty(value = "currency")
  private String currency;

}
