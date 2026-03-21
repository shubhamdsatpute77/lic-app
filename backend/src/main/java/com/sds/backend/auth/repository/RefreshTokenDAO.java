package com.sds.backend.auth.repository;

import com.sds.backend.auth.entity.RefreshToken;
import com.sds.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenDAO extends JpaRepository<RefreshToken, Long> {

    void deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);
}
