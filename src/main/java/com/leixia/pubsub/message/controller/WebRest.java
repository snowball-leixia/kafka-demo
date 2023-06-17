package com.leixia.pubsub.message.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.leixia.pubsub.message.http.SuccessResponse;
import com.leixia.pubsub.message.kafka.MessagePublisher;
import com.leixia.pubsub.message.kpi.KpiMetricsService;
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
  private final KpiMetricsService kpiMetricsService;

  public WebRest(MessagePublisher messagePublisher, KpiMetricsService kpiMetricsService) {
    this.messagePublisher = messagePublisher;
    this.kpiMetricsService = kpiMetricsService;
  }

  @GetMapping(value = "/handlePrice", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> handleGeNMtCall() throws JsonProcessingException {
    final String ticket = messagePublisher.publish();
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setMessage("price message published");
    successResponse.setRef(ticket);
    successResponse.setStatusCode(HttpStatus.OK.value());
    kpiMetricsService.recordSuccess();
    return ResponseEntity.ok().body(successResponse);
  }

  @GetMapping(value = "/success", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> success() {
    kpiMetricsService.recordSuccess();
    return ResponseEntity.ok("ok");
  }

  @GetMapping(value = "/fail", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> fail() {
    kpiMetricsService.recordFail();
    return ResponseEntity.ok("fail");
  }
}

