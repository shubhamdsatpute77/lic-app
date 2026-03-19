package com.sds.backend.common;

import java.time.LocalDateTime;

public record ApiResponse<T> (
        String requestId,
        LocalDateTime timestamp,
        Long executionTimeMs,
        String message,
        String path,
        T data
) {
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
