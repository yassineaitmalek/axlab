package com.airxelerate.persistence.presentation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {

  private String message;

  private String status;

  private Integer httpStatus;

  private String path;

  @Builder.Default
  private String date = LocalDate.now().toString();

  @Builder.Default
  private String time = LocalTime.now().toString();

  @Builder.Default
  private String zone = ZoneId.systemDefault().toString();

  @Builder.Default
  private List<ValidationFieldError> validationFieldErrors = new ArrayList<>();

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ValidationFieldError {

    private String field;

    private String message;
  }
}
