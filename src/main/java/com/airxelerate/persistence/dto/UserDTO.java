package com.airxelerate.persistence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private List<String> roles;
}
