package com.mori5.itsecurity.logging.interceptors;

import com.mori5.itsecurity.logging.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private final LoggingService loggingService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       loggingService.logRequest(request, response);
    }

}
