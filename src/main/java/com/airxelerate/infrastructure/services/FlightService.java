package com.airxelerate.infrastructure.services;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.airxelerate.persistence.dto.FlightDTO;
import com.airxelerate.persistence.dto.FlightSearchDTO;
import com.airxelerate.persistence.dto.FlightUpdateDTO;
import com.airxelerate.persistence.exception.config.ResourceNotFoundException;
import com.airxelerate.persistence.models.Flight;
import com.airxelerate.persistence.repositories.FlightRepository;
import com.airxelerate.persistence.specification.FlightSpecification;

import lombok.RequiredArgsConstructor;

@Validated
@Service
@RequiredArgsConstructor
public class FlightService {

  private final FlightRepository flightRepository;

  private final FlightSpecification flightSpecification;

  private final ModelMapper modelMapper;

  public Page<Flight> search(FlightSearchDTO searchDTO, Pageable pageable) {
    return flightRepository.findAll(flightSpecification.searchRequest(searchDTO), pageable);
  }

  public Flight get(String id) {
    return flightRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Flight does not exist"));
  }

  public Flight createFlight(@Valid FlightDTO flightDTO) {

    Flight Flight = modelMapper.map(flightDTO, Flight.class);
    return flightRepository.save(Flight);
  }

  public void delete(String id) {

    Flight Flight = get(id);
    flightRepository.delete(Flight);

  }

  public Flight update(String id, @Valid FlightUpdateDTO flightUpdateDTO) {

    Flight old = get(id);
    modelMapper.map(flightUpdateDTO, old);
    return flightRepository.save(old);

  }

}
