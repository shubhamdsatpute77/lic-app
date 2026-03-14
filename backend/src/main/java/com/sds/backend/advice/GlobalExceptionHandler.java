package com.sds.backend.advice;

import com.sds.backend.common.ApiResponse;
import com.sds.backend.dto.RegisterUserRequest;
import com.sds.backend.exception.BussinessValidationException;
import com.sds.backend.filter.RequestTimingFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BussinessValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessValidationErrors(BussinessValidationException ex,
                                                                              HttpServletRequest request) {
        Long startTime = (Long) request.getAttribute(RequestTimingFilter.REQUEST_START_TIME);
        long executionTime = System.currentTimeMillis() - startTime;
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponse.prepare(
                                executionTime,
                                ex.getMessage(),
                                request.getRequestURI(),
                                ex.getInvalidData()
                        )
                );
    }
}
