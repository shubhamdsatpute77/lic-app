package com.sds.backend.controller;

import com.sds.backend.common.ApiResponseMeta;
import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.dto.UserResponse;
import com.sds.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("me")
    public String hello() {
        return "Hello World!";
    }
}
