package com.airxelerate.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.airxelerate.persistence.models.Role;
import com.airxelerate.persistence.repositories.config.AbstractRepository;

@Repository
public interface RoleRepository extends AbstractRepository<Role, String> {

    Optional<Role> findTopByName(String name);

    List<Role> findAllByNameIn(List<String> values);

}
