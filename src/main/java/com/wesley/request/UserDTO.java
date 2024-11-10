package com.wesley.request;

import com.wesley.model.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserDTO(@NotBlank(message = "Fullname is required")
                      String fullName,
                      @NotNull(message = "Email is required")
                      @Email
                      String email,
                      @NotNull(message = "Password is required")
                      String password,

                      USER_ROLE role) {
}
