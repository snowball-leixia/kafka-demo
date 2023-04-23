package com.leixia.pubsub.message.http;

import java.time.LocalDateTime;
import java.util.TimeZone;
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
  private LocalDateTime timestamp = LocalDateTime.now(TimeZone.getDefault().toZoneId());
}
