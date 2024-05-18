package com.example.dto.auth;

import com.example.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String login;
    private String phone;
    private String id;
    private String name;
    private String surname;
    private RoleEnum roleList;
    private String jwt;
}
