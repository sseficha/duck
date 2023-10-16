package com.solonsef.duck.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class UserCreationDTO {

    @NotBlank(message = "Username should not be blank")
    private String username;
    @NotBlank(message = "Password should not be blank")
    private String password;

    @Email(message = "Please use a valid email")
    @NotBlank(message = "Email should not be blank")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
