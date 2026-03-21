package com.sds.backend.auth.dto.request;

public record LogoutRequest(
        String refreshToken,
        String email
) {}
