package com.sds.backend.service;

import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.dto.UserResponse;
import com.sds.backend.entity.User;
import com.sds.backend.enums.UserRole;
import com.sds.backend.exception.BussinessValidationException;
import com.sds.backend.mapper.UserMapper;
import com.sds.backend.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public UserResponse register(RegisterUserRequest registerUserRequest) {
        if (userDAO.existsByEmail(registerUserRequest.email())) {
            throw new BussinessValidationException("User with email already exist", registerUserRequest);
        }
        Optional<User> manager = userDAO.findByEmail(registerUserRequest.managerEmail());
        if (registerUserRequest.role() == UserRole.USER) {
            if (manager.isEmpty()) {
                throw new BussinessValidationException("Manager with email not found", registerUserRequest);
            }
            if (manager.get().getRole() != UserRole.ADMIN) {
                throw new BussinessValidationException("Please select valid manager email", registerUserRequest);
            }
        }
        User user = userDAO.save(UserMapper.toEntity(registerUserRequest, manager));
        return UserMapper.toResponse(user);
    }
}
