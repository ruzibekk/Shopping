package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {

    private String id;
    private String name;
    private String surname;
    private String role;
    private String login;
    private String phone;
    private String password;
    private LocalDateTime createdDate;
}
