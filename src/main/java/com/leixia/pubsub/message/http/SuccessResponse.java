package com.leixia.pubsub.message.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse {

  private String message = "";
  private int statusCode;
  private String ref;
}
