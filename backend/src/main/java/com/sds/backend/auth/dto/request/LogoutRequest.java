package com.sds.backend.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogoutRequest (
        @NotBlank
        String refreshToken,

        @NotBlank
        @Email
        String email
) {}
