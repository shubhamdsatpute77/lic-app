package com.sds.backend.controller;

import com.sds.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello ADMIN!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("user")
    public String helloUser() {
        return "Hello USER!";
    }
}
