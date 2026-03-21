package com.sds.backend.auth.dto.request;

public record RefreshTokenRequest(
        String refreshToken,
        String email
) {}
