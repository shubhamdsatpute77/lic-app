package com.sds.backend.auth.dto.response;

import com.sds.backend.dto.response.UserResponse;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserResponse user
) {}
