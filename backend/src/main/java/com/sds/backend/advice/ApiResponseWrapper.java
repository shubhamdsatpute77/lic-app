package com.sds.backend.advice;

import com.sds.backend.common.ApiResponse;
import com.sds.backend.common.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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

        return ApiResponse.prepare(
                RequestContext.getRequestId(request),
                RequestContext.getExecutionTime(request),
                path,
                body
        );
    }
}
