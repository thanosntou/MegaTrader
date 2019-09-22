package com.ntouzidis.cooperative.module.common.exceptions;

public class NotFoundException extends RuntimeException {

  static final long serialVersionUID = 1L;

  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }

  public NotFoundException(String message, Throwable cause,
                           boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
