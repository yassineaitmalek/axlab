package com.airxelerate.persistence.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.airxelerate.persistence.models.User;
import com.airxelerate.persistence.repositories.config.AbstractRepository;

@Repository
public interface UserRepository extends AbstractRepository<User, String> {

    Optional<User> findTopByUsername(String username);

}
