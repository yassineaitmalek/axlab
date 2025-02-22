package com.airxelerate.presentation.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.access.AccessDeniedException;
import com.airxelerate.persistence.exception.config.ClientSideException;
import com.airxelerate.persistence.exception.config.ResourceNotFoundException;
import com.airxelerate.persistence.exception.config.ServerSideException;
import com.airxelerate.persistence.exception.config.TooManyRequestsException;
import com.airxelerate.persistence.presentation.ApiExceptionResponse;
import com.airxelerate.presentation.config.AbstractController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler implements AbstractController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiExceptionResponse> handleExceptions(Exception e, WebRequest request) {
    log.error(e.getMessage(), e);
    return internalException(e, request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiExceptionResponse> handleExceptions(AccessDeniedException e, WebRequest request) {
    log.error(e.getMessage());
    return forbidden(e, request);
  }

  @ExceptionHandler(value = { ServerSideException.class })
  public ResponseEntity<ApiExceptionResponse> handleServerSideException(
      ServerSideException e, WebRequest request) {

    log.error(e.getMessage());
    return internalException(e, request);
  }

  @ExceptionHandler(value = { ClientSideException.class })
  public ResponseEntity<ApiExceptionResponse> handleClientSideException(
      ClientSideException e, WebRequest request) {

    log.error(e.getMessage());
    return badRequest(e, request);
  }

  @ExceptionHandler(value = { TooManyRequestsException.class })
  public ResponseEntity<ApiExceptionResponse> handleTooManyRequestsException(
      TooManyRequestsException e, WebRequest request) {

    log.error(e.getMessage());
    return tooManyRequests(e, request);
  }

  @ExceptionHandler(value = { ResourceNotFoundException.class })
  public ResponseEntity<ApiExceptionResponse> handleResourceNotFoundException(
      ResourceNotFoundException e, WebRequest request) {

    log.error(e.getMessage());
    return notFound(e, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
      ConstraintViolationException e, WebRequest request) {
    log.error(e.getMessage());
    return badRequest(e, request);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(
      BindException e, WebRequest request) {

    log.error(e.getMessage());
    return badRequest(e, request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
      WebRequest request) {
    StringBuilder sb = new StringBuilder();
    ex.getBindingResult().getFieldErrors().forEach(error -> sb.append("error : ")
        .append(error.getField())
        .append("   ")
        .append("Message : ")
        .append(error.getDefaultMessage())
        .append("\n"));
    return badRequest(sb.toString(), ex, request);
  }
}
