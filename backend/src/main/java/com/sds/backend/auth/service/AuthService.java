package com.sds.backend.auth.service;

import com.sds.backend.auth.dto.request.LoginRequest;
import com.sds.backend.auth.dto.request.RegisterUserRequest;
import com.sds.backend.dto.response.UserResponse;
import com.sds.backend.mapper.UserMapper;
import com.sds.backend.auth.dto.response.AuthResponse;
import com.sds.backend.auth.dto.CustomUserDetails;
import com.sds.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterUserRequest request) {
        UserResponse userResponse = userService.register(request);
        String token = jwtService.generateToken(request.email());
        return new AuthResponse(token, userResponse);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(request.email());
        return new AuthResponse(token, UserMapper.toResponse(customUserDetails.getUser()));
    }
}
