package com.airxelerate.persistence.specification;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.airxelerate.persistence.dto.FlightSearchDTO;
import com.airxelerate.persistence.models.Flight;
import com.airxelerate.persistence.models.Flight_;

@Component
public class FlightSpecification implements AbstractSpecification<Flight> {

  private Specification<Flight> hasCarrierCode(String carrierCode) {
    return (root, query, builder) -> builder.like(root.get(Flight_.CARRIER_CODE), like(carrierCode));
  }

  private Specification<Flight> hasFlightNumber(Long flightNumber) {
    return (root, query, builder) -> builder.equal(root.get(Flight_.FLIGHT_NUMBER), flightNumber);
  }

  private Specification<Flight> hasFlightDate(LocalDate flightDate) {
    return (root, query, builder) -> builder.equal(root.get(Flight_.FLIGHT_DATE), flightDate);
  }

  private Specification<Flight> hasOrigin(String origin) {
    return (root, query, builder) -> builder.like(root.get(Flight_.ORIGIN), like(origin));
  }

  private Specification<Flight> hasDestination(String destination) {
    return (root, query, builder) -> builder.like(root.get(Flight_.DESTINATION), like(destination));
  }

  public Specification<Flight> searchRequest(FlightSearchDTO search) {
    return Optional.ofNullable(search)
        .map(this::searchRequestImpl)
        .orElseGet(this::unitSpecification);
  }

  private Specification<Flight> searchRequestImpl(FlightSearchDTO search) {
    return Stream.of(
        transformer(search.getCarrierCode(), this::hasCarrierCode),
        transformer(search.getFlightNumber(), this::hasFlightNumber),
        transformer(search.getFlightDate(), this::hasFlightDate),
        transformer(search.getOrigin(), this::hasOrigin),
        transformer(search.getDestination(), this::hasDestination))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(Specification.where(distinct()), Specification::and);
  }
}
