package com.sds.backend.controller;

import com.sds.backend.common.ApiResponseMeta;
import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.dto.UserResponse;
import com.sds.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    @ApiResponseMeta(message = "User registered successfully")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerUserRequest));
    }
}
