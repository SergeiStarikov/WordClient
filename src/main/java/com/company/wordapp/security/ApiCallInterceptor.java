package com.company.wordapp.security;

import com.company.wordapp.validator.impl.ApiKeyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class ApiCallInterceptor extends HandlerInterceptorAdapter {

    private static final String API_KEY_ERROR_MESSAGE = "Api key is required";
    private static final String API_KEY = "api-key";
    private final ApiKeyValidator apiKeyValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getDispatcherType().name().equals("REQUEST")) {
            if (apiKeyValidator.isValid(request.getHeader(API_KEY))) {
                return true;
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(API_KEY_ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
