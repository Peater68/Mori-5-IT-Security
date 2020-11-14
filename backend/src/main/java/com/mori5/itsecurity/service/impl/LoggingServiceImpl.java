package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.domain.RequestLog;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.repository.RequestLogRepository;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.security.AuthUserDetails;
import com.mori5.itsecurity.security.AuthenticationFacade;
import com.mori5.itsecurity.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {

    private final UserRepository userRepository;
    private final RequestLogRepository requestLogRepository;
    private final AuthenticationFacade authenticationFacade;

    private final List<String> specialLoggingPresentedFor = Arrays.asList(
            "/error",
            "/api/auth/login"
    );

    @Override
    public void logRequest(HttpServletRequest request, HttpServletResponse response) {
        if (specialLoggingPresentedFor.contains(request.getServletPath())) {
            return; // Do not log.
        }

        User currentUser = getCurrentUser();
        requestLogRepository.save(
                RequestLog.builder()
                        .actor(currentUser)
                        .method(request.getMethod())
                        .path(request.getServletPath())
                        .responseCode(response.getStatus())
                        .build()
        );
    }

    private User getCurrentUser() {
        AuthUserDetails currentUserDetails = authenticationFacade.getCurrentUserFromContext();
        if (currentUserDetails == null) {
            return null;
        }

        return userRepository.findById(currentUserDetails.getUserId()).orElse(null);
    }
    
}
