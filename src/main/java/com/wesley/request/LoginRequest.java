package com.wesley.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest (@NotBlank(message = "Email is required")
                            @Email(message = "Email is invalid")
                            String email,
                            @NotNull(message = "Password is required")
                            String password) {}


