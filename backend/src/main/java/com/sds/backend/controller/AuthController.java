package com.sds.backend.controller;

import com.sds.backend.common.ApiResponseMeta;
import com.sds.backend.dto.LoginRequest;
import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.dto.UserResponse;
import com.sds.backend.security.JwtService;
import com.sds.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("register")
    @ApiResponseMeta(message = "User registered successfully")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerUserRequest));
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        String token = jwtService.generateToken(loginRequest.email());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
    }
}
