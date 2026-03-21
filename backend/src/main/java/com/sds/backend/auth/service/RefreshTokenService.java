package com.sds.backend.auth.service;

import com.sds.backend.auth.dto.request.RefreshTokenRequest;
import com.sds.backend.auth.entity.RefreshToken;
import com.sds.backend.auth.repository.RefreshTokenDAO;
import com.sds.backend.entity.User;
import com.sds.backend.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${refresh.token.expiration.days}")
    private long expirationDays;

    private final RefreshTokenDAO refreshTokenDAO;
    private final UserDAO userDAO;

    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = refreshTokenDAO.findByUser(user).orElse(new RefreshToken());
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(expirationDays));
        return refreshTokenDAO.save(token);
    }

    public RefreshToken verifyRefreshToken(RefreshTokenRequest request) {
        User user = userDAO.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found with refresh token"));
        RefreshToken refreshToken = refreshTokenDAO.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (!refreshToken.getToken().equals(request.refreshToken())
                || refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh accessToken expired");
        }
        return refreshToken;
    }
}
