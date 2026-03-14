package com.sds.backend.common;

import java.time.LocalDateTime;

public record ApiResponse<T> (
        LocalDateTime timestamp,
        String message,
        String path,
        T data
) {
    public static <T> ApiResponse<T> prepare(String path, T data) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                "Success",
                path,
                data
        );
    }

    public static <T> ApiResponse<T> prepare(String message, String path, T data) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                message,
                path,
                data
        );
    }

    public static <T> ApiResponse<T> prepare(String message, String path) {
        return new ApiResponse<>(
                LocalDateTime.now(),
                message,
                path,
                null
        );
    }
}
