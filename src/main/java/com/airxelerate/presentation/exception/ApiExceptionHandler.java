package com.airxelerate.presentation.exception;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.airxelerate.persistence.exception.config.ClientSideException;
import com.airxelerate.persistence.exception.config.ResourceNotFoundException;
import com.airxelerate.persistence.exception.config.ServerSideException;
import com.airxelerate.persistence.exception.config.TooManyRequestsException;
import com.airxelerate.persistence.presentation.ApiExceptionResponse;
import com.airxelerate.persistence.presentation.ApiExceptionResponse.ValidationFieldError;
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
      ConstraintViolationException ex, WebRequest request) {
    log.error(ex.getMessage());
    List<ValidationFieldError> validationFieldErrors = Optional.ofNullable(ex)
        .map(ConstraintViolationException::getConstraintViolations)
        .orElseGet(Collections::emptySet)
        .stream()
        .map(error -> new ValidationFieldError(error.getPropertyPath().toString(), error.getMessage()))
        .collect(Collectors.toList());
    return validationErrors(validationFieldErrors, ex, request);
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

    List<ValidationFieldError> validationFieldErrors = Optional.ofNullable(ex)
        .map(MethodArgumentNotValidException::getBindingResult)
        .map(BindingResult::getFieldErrors)
        .orElseGet(Collections::emptyList)
        .stream()
        .map(error -> new ValidationFieldError(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());
    return validationErrors(validationFieldErrors, ex, request);
  }
}
