package com.sds.backend.security.model;

import com.sds.backend.dto.response.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) {}
