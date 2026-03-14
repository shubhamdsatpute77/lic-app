package com.sds.backend.common;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApiResponse<T> (
        String requestId,
        LocalDateTime timestamp,
        Long executionTimeMs,
        String message,
        String path,
        T data
) {
    public static <T> ApiResponse<T> prepare(String requestId, Long executionTimeMs, String path, T data) {
        return new ApiResponse<>(
                requestId,
                LocalDateTime.now(),
                executionTimeMs,
                "Success",
                path,
                data
        );
    }

    public static <T> ApiResponse<T> prepare(String requestId, Long executionTimeMs, String message, String path, T data) {
        return new ApiResponse<>(
                requestId,
                LocalDateTime.now(),
                executionTimeMs,
                message,
                path,
                data
        );
    }
}
