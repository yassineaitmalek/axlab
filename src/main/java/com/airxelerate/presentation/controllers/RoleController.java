package com.airxelerate.presentation.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airxelerate.infrastructure.services.RoleService;
import com.airxelerate.persistence.dto.RoleDTO;
import com.airxelerate.persistence.models.Role;
import com.airxelerate.persistence.presentation.ApiDataResponse;
import com.airxelerate.presentation.config.AbstractController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController implements AbstractController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Role>>> getRoles(Pageable pageable) {
        return ok(() -> roleService.getRoles(pageable));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiDataResponse<Role>> createRole(@RequestBody RoleDTO roleDTO) {
        return create(() -> roleService.createRole(roleDTO));
    }

}
