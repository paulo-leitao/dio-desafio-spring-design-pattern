package com.springboot.springdesignpatterns.exception;

public class BusinessException extends RuntimeException{
  public BusinessException(String message) {
    super(message);
  }
  
}
