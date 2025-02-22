package com.airxelerate.infrastructure.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.airxelerate.infrastructure.security.service.UserContainer;
import com.airxelerate.persistence.dto.UserDTO;
import com.airxelerate.persistence.dto.UserUpdateDTO;
import com.airxelerate.persistence.exception.config.ResourceNotFoundException;
import com.airxelerate.persistence.exception.config.ServerSideException;
import com.airxelerate.persistence.models.User;
import com.airxelerate.persistence.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    public User get(String id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResourceNotFoundException("User NOT FOUND"));
    }

    public User getCurrentUser() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(UserContainer.class::isInstance)
                .map(UserContainer.class::cast)
                .map(UserContainer::getUser)
                .orElseThrow(() -> new ServerSideException("the current user is not  authenticated"));

    }

    public User createUser(UserDTO userDTO) {

        User user = new User();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roleService.getRoles(userDTO.getRoles()));
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setUsername(userDTO.getUsername());
        return userRepository.save(user);

    }

    public void deleteUser(String id) {
        Optional.of(get(id)).ifPresent(userRepository::delete);

    }

    public User updateUser(String id, UserUpdateDTO userUpdateDTO) {

        User user = get(id);
        modelMapper.map(userUpdateDTO, user);
        return userRepository.save(user);

    }

}
