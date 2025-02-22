package com.airxelerate.presentation.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airxelerate.infrastructure.services.FlightService;
import com.airxelerate.persistence.dto.FlightDTO;
import com.airxelerate.persistence.dto.FlightSearchDTO;
import com.airxelerate.persistence.dto.FlightUpdateDTO;
import com.airxelerate.persistence.models.Flight;
import com.airxelerate.persistence.presentation.ApiDataResponse;
import com.airxelerate.presentation.config.AbstractController;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController implements AbstractController {

  private final FlightService flightService;

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @PostMapping
  public ResponseEntity<ApiDataResponse<Flight>> create(@RequestBody @Valid FlightDTO flightDTO) {
    return create(() -> flightService.createFlight(flightDTO));
  }

  @GetMapping
  public ResponseEntity<ApiDataResponse<Page<Flight>>> search(@ModelAttribute FlightSearchDTO flightSearchDTO,
      Pageable pageable) {
    return ok(() -> flightService.search(flightSearchDTO, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiDataResponse<Flight>> get(@PathVariable String id) {
    return ok(() -> flightService.get(id));
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @PatchMapping("/{id}")
  public ResponseEntity<ApiDataResponse<Flight>> update(@PathVariable String id,
      @RequestBody @Valid FlightUpdateDTO flightUpdateDTO) {
    return ok(() -> flightService.update(id, flightUpdateDTO));
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    return noContent(() -> flightService.delete(id));
  }

}
