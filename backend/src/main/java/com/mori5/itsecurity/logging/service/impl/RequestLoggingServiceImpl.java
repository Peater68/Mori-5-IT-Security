package com.mori5.itsecurity.logging.service.impl;

import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.domain.RequestLog;
import com.mori5.itsecurity.logging.repository.RequestLogRepository;
import com.mori5.itsecurity.logging.service.RequestLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestLoggingServiceImpl implements RequestLoggingService {

    private final RequestLogRepository requestLogRepository;

    private final List<String> specialLoggingPresentedFor = Arrays.asList(
            "/error",
            "/api/auth/login"
    );

    @Override
    public void logRequest(User actor, HttpServletRequest request, HttpServletResponse response) {
        if (specialLoggingPresentedFor.contains(request.getServletPath())) {
            return; // Do not log.
        }

        requestLogRepository.save(
                RequestLog.builder()
                        .actor(actor)
                        .method(request.getMethod())
                        .path(request.getServletPath())
                        .responseCode(response.getStatus())
                        .build()
        );
    }

}
