package com.sds.backend.advice;

import com.sds.backend.common.ApiResponse;
import com.sds.backend.filter.RequestTimingFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.sds.backend.common.RequestContext.ID;
import static com.sds.backend.common.RequestContext.START_TIME;

@RestControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverRequest,
                                  ServerHttpResponse serverResponse) {
        if (body instanceof ApiResponse) return body;
        if (body instanceof String) return body;

        String path = serverRequest.getURI().getPath();
        HttpServletRequest request = ((ServletServerHttpRequest) serverRequest).getServletRequest();
        Long startTime = (Long) request.getAttribute(START_TIME);
        long executionTime = System.currentTimeMillis() - startTime;
        String requestId = (String) request.getAttribute(ID);

        return ApiResponse.prepare(requestId, executionTime, path, body);
    }
}
