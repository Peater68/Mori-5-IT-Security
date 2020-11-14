package com.mori5.itsecurity.interceptors;

import com.mori5.itsecurity.service.LoggingService;
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
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       loggingService.logRequest(request, response);
    }

}
