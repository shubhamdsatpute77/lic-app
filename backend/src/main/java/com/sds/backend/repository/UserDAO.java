package com.sds.backend.repository;

import com.sds.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
