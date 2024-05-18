package com.example.dto.auth;

import com.example.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "phoneNumber required")
    @Size(min = 9, max = 13, message = "phoneNumber not valid")
    private String phoneNumber;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password size min 6 chars.")
    private String password;
    @NotBlank(message = "Login required")
    private String login;
    @NotNull(message = "Gender required")
    private GenderType gender;
}
