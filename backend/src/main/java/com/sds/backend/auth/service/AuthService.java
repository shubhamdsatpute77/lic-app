package com.sds.backend.auth.service;

import com.sds.backend.auth.dto.request.LoginRequest;
import com.sds.backend.auth.dto.request.RefreshTokenRequest;
import com.sds.backend.auth.dto.request.RegisterUserRequest;
import com.sds.backend.auth.entity.RefreshToken;
import com.sds.backend.entity.User;
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
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterUserRequest request) {
        User user = userService.register(request);
        String token = jwtService.generateToken(request.email());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new AuthResponse(token, refreshToken.getToken(), UserMapper.toResponse(user));
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
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(customUserDetails.getUser());
        return new AuthResponse(token, refreshToken.getToken(), UserMapper.toResponse(customUserDetails.getUser()));
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request);
        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateToken(user.getEmail());
        return new AuthResponse(newAccessToken, request.refreshToken(), UserMapper.toResponse(user));
    }
}
