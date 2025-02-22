package com.airxelerate.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.airxelerate.persistence.models.Flight;
import com.airxelerate.persistence.repositories.config.AbstractRepository;

@Repository
public interface FlightRepository extends AbstractRepository<Flight, String> {

}
