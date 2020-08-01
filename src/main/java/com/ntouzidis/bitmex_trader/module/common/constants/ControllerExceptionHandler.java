package com.ntouzidis.bitmex_trader.module.common.constants;

import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.ntouzidis.bitmex_trader.CooperativeApplication.logger;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> handleMissingRequestParam(Exception ex) {
    logger.info(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(RuntimeException ex) {
    logger.info(ex.getMessage(), ex);
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

}
