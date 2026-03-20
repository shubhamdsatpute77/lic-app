package com.sds.backend.auth.dto.request;

import com.sds.backend.enums.UserRole;
import com.sds.backend.validators.ValidRegisterUserRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@ValidRegisterUserRequest
public record RegisterUserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        String managerEmail,

        UserRole role
) {
}
