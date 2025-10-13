package com.splitspend.auth.controller;

import com.splitspend.auth.service.exception.UserAuthenticationException;
import com.splitspend.auth.service.exception.UserNotRegisteredServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ErrorResponse response =
        (ErrorResponse) ex.getBindingResult().getFieldErrors().stream().map(FieldError::toString);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UserNotRegisteredServiceException.class)
  public ResponseEntity<ErrorResponse> handleUserNotRegisteredException(
      UserNotRegisteredServiceException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(UserAuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleUserAuthenticationException(
      UserAuthenticationException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  static class ErrorResponse {
    private String message;

    ErrorResponse(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }
}
