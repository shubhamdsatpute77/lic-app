package com.sds.backend.enums;

public enum UserRole {
    USER,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
