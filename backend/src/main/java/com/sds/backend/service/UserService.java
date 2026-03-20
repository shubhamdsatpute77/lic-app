package com.sds.backend.service;

import com.sds.backend.auth.dto.request.RegisterUserRequest;
import com.sds.backend.dto.response.UserResponse;
import com.sds.backend.entity.User;
import com.sds.backend.enums.UserRole;
import com.sds.backend.exception.BadRequestException;
import com.sds.backend.exception.ResourceNotFoundException;
import com.sds.backend.mapper.UserMapper;
import com.sds.backend.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterUserRequest registerUserRequest) {
        if (userDAO.existsByEmail(registerUserRequest.email())) {
            throw new BadRequestException("User with email already exist", registerUserRequest);
        }
        Optional<User> manager = userDAO.findByEmail(registerUserRequest.managerEmail());
        if (registerUserRequest.role() == UserRole.USER) {
            if (manager.isEmpty()) {
                throw new ResourceNotFoundException("Manager with email not found", registerUserRequest);
            }
            if (manager.get().getRole() != UserRole.ADMIN) {
                throw new BadRequestException("Please select valid manager email", registerUserRequest);
            }
        }
        User user = UserMapper.toEntity(registerUserRequest, manager);
        user.setPassword(passwordEncoder.encode(registerUserRequest.password()));
        userDAO.save(user);
        return UserMapper.toResponse(user);
    }
}
