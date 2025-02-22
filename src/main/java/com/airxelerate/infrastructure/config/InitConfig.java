package com.airxelerate.infrastructure.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.airxelerate.infrastructure.services.RoleService;
import com.airxelerate.infrastructure.services.UserService;
import com.airxelerate.persistence.dto.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitConfig {

  private final RoleService roleService;

  private final UserService userService;

  @PostConstruct
  public void init() {
    try {
      roleService.createRoles(RoleService.ADMIN, RoleService.REGULAR_USER);

      UserDTO admin = UserDTO.builder()
          .username("admin")
          .password("admin")
          .firstName("ADMIN")
          .lastName("ADMIN")
          .roles(Arrays.asList(RoleService.ADMIN))
          .build();
      UserDTO user = UserDTO.builder()
          .username("user")
          .password("user")
          .firstName("user")
          .lastName("user")
          .roles(Arrays.asList(RoleService.REGULAR_USER))
          .build();

      userService.createUser(admin);
      userService.createUser(user);

    } catch (Exception e) {
      log.error("admin & regular user are already created");

    }

  }

}
