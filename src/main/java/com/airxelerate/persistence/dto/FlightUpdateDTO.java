package com.airxelerate.persistence.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightUpdateDTO {

  @Size(min = 2, max = 3, message = "Carrier code must be 2 or 3 characters")
  @Pattern(regexp = "^[A-Z0-9]+$", message = "Carrier code must be alphanumeric")
  private String carrierCode;

  @Min(value = 0, message = "Flight number must be at least 0")
  @Max(value = 9999, message = "Flight number must be at most 9999")
  private Long flightNumber;

  private LocalDate flightDate;

  @Size(min = 3, max = 3, message = "Origin airport code must be exactly 3 characters")
  @Pattern(regexp = "^[A-Z]+$", message = "Origin airport code must be uppercase letters")
  private String origin;

  @Size(min = 3, max = 3, message = "Destination airport code must be exactly 3 characters")
  @Pattern(regexp = "^[A-Z]+$", message = "Destination airport code must be uppercase letters")
  private String destination;
}
