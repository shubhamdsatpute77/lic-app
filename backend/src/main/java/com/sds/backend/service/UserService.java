package com.sds.backend.service;

import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.entity.User;
import com.sds.backend.enums.UserRole;
import com.sds.backend.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public void register(RegisterUserRequest registerUserRequest) {
        if (userDAO.existsByEmail(registerUserRequest.email())) {
            throw new RuntimeException("User with email already exist");
        }
        User manager = userDAO.findByEmail(registerUserRequest.managerEmail()).orElseThrow(
                () -> new IllegalArgumentException("Manager not found")
        );
        if (manager.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Please select valid manager email");
        }
        User user = User.builder()
                .firstName(registerUserRequest.firstName())
                .lastName(registerUserRequest.lastName())
                .email(registerUserRequest.email())
                .password(registerUserRequest.password())
                .manager(manager)
                .role(registerUserRequest.role())
                .build();
        userDAO.save(user);
    }
}
