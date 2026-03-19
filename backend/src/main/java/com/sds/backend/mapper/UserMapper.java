package com.sds.backend.mapper;

import com.sds.backend.dto.request.RegisterUserRequest;
import com.sds.backend.dto.response.UserResponse;
import com.sds.backend.entity.User;

import java.util.Objects;
import java.util.Optional;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                Objects.nonNull(user.getManager()) ? toResponse(user.getManager()) : null
        );
    }

    public static User toEntity(RegisterUserRequest request, Optional<User> manager) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .manager(manager.orElse(null))
                .role(request.role())
                .build();
    }
}
