package com.sds.backend.advice;

import com.sds.backend.common.ApiResponse;
import com.sds.backend.common.RequestContext;
import com.sds.backend.exception.BadRequestException;
import com.sds.backend.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.prepare(
                                RequestContext.getRequestId(request),
                                RequestContext.getExecutionTime(request),
                                "Validation failed",
                                request.getRequestURI(),
                                errors
                        )
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessValidationErrors(BadRequestException ex,
                                                                              HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.prepare(
                                RequestContext.getRequestId(request),
                                RequestContext.getExecutionTime(request),
                                ex.getMessage(),
                                request.getRequestURI(),
                                ex.getInvalidData()
                        )
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundErrors(ResourceNotFoundException ex,
                                                                            HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.prepare(
                                RequestContext.getRequestId(request),
                                RequestContext.getExecutionTime(request),
                                ex.getMessage(),
                                request.getRequestURI(),
                                ex.getInvalidData()
                        )
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsError(BadCredentialsException ex,
                                                                         HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.prepare(
                                RequestContext.getRequestId(request),
                                RequestContext.getExecutionTime(request),
                                ex.getMessage(),
                                request.getRequestURI(),
                                null
                        )
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundError(UsernameNotFoundException ex,
                                                                       HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.prepare(
                                RequestContext.getRequestId(request),
                                RequestContext.getExecutionTime(request),
                                ex.getMessage(),
                                request.getRequestURI(),
                                null
                        )
                );
    }
}
