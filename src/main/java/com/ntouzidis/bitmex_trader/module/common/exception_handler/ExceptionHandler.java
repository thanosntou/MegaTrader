package com.ntouzidis.bitmex_trader.module.common.exception_handler;

import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }

}
