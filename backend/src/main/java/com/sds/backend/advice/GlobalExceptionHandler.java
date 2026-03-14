package com.sds.backend.advice;

import com.sds.backend.common.ApiResponse;
import com.sds.backend.common.RequestContext;
import com.sds.backend.exception.BussinessValidationException;
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
        return ResponseEntity
                .badRequest()
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
}
