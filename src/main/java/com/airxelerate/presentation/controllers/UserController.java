package com.airxelerate.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airxelerate.infrastructure.services.UserService;
import com.airxelerate.persistence.dto.UserDTO;
import com.airxelerate.persistence.dto.UserUpdateDTO;
import com.airxelerate.persistence.models.User;
import com.airxelerate.persistence.presentation.ApiDataResponse;
import com.airxelerate.presentation.config.AbstractController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements AbstractController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<User>> createUser(@RequestBody UserDTO userDTO) {
        return create(() -> userService.createUser(userDTO));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return noContent(() -> userService.deleteUser(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR') or #id == authentication.principal.id")
    public ResponseEntity<ApiDataResponse<User>> getUser(@PathVariable String id) {

        return ok(() -> userService.get(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR') or #id == authentication.principal.id")
    public ResponseEntity<ApiDataResponse<User>> updateUser(@PathVariable String id,
            @RequestBody UserUpdateDTO userUpdateDTO) {

        return ok(() -> userService.updateUser(id, userUpdateDTO));
    }

}
