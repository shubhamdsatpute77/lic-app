package com.sds.backend.service;

import com.sds.backend.dto.UserDto;
import com.sds.backend.entity.User;
import com.sds.backend.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public void register(UserDto userDto) {
        if (userDAO.existsByEmail(userDto.email())) {
            throw new RuntimeException("User with email already exist");
        }
        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(userDto.password())
                .build();
        userDAO.save(user);
    }
}
