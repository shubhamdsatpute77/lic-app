package com.sds.backend.dto;

import com.sds.backend.enums.UserRole;

public record UserResponse (
        Long id,
        String firstName,
        String lastName,
        String email,
        UserRole role,
        UserResponse manager
) {}
