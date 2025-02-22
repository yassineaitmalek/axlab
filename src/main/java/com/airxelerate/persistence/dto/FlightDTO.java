package com.airxelerate.persistence.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

  @NotBlank(message = "Carrier code is required")
  @Size(min = 2, max = 3, message = "Carrier code must be 2 or 3 characters")
  @Pattern(regexp = "^[A-Z0-9]+$", message = "Carrier code must be alphanumeric")
  private String carrierCode;

  @NotNull(message = "Flight number is required")
  @Min(value = 0, message = "Flight number must be at least 0")
  @Max(value = 9999, message = "Flight number must be at most 9999")
  private Long flightNumber;

  @NotNull(message = "Flight date is required")
  private LocalDate flightDate;

  @NotBlank(message = "Origin airport code is required")
  @Size(min = 3, max = 3, message = "Origin airport code must be exactly 3 characters")
  @Pattern(regexp = "^[A-Z]+$", message = "Origin airport code must be uppercase letters")
  private String origin;

  @NotBlank(message = "Destination airport code is required")
  @Size(min = 3, max = 3, message = "Destination airport code must be exactly 3 characters")
  @Pattern(regexp = "^[A-Z]+$", message = "Destination airport code must be uppercase letters")
  private String destination;
}
