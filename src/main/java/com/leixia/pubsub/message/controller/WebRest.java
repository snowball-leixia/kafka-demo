package com.leixia.pubsub.message.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.leixia.pubsub.message.http.SuccessResponse;
import com.leixia.pubsub.message.kafka.MessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class WebRest {

  private final MessagePublisher messagePublisher;

  public WebRest(MessagePublisher messagePublisher) {
    this.messagePublisher = messagePublisher;
  }

  @GetMapping(value = "/handlePrice", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> handleGetCall() throws JsonProcessingException {
    String ticket = messagePublisher.publish();
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setMessage("price message published");
    successResponse.setRef(ticket);
    successResponse.setStatusCode(HttpStatus.OK.value());
    return ResponseEntity.ok().body(successResponse);
  }

}
