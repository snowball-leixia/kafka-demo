package com.leixia.pubsub.message.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ServiceControllerAdvise {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<Void> handleRequestValidationException(ConstraintViolationException ex) {
    log.error(ex.getMessage());
    return ResponseEntity.badRequest().build();
  }
}
