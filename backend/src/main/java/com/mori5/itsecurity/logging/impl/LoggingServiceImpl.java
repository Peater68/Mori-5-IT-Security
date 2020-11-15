package com.mori5.itsecurity.logging.impl;

import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.log.CommentLog;
import com.mori5.itsecurity.domain.log.RequestLog;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.CommentLoggingService;
import com.mori5.itsecurity.logging.RequestLoggingService;
import com.mori5.itsecurity.repository.CommentLogRepository;
import com.mori5.itsecurity.repository.RequestLogRepository;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.security.AuthUserDetails;
import com.mori5.itsecurity.security.AuthenticationFacade;
import com.mori5.itsecurity.logging.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    private final RequestLoggingService requestLoggingService;
    private final CommentLoggingService commentLoggingService;

    @Override
    public void logRequest(HttpServletRequest request, HttpServletResponse response) {
        requestLoggingService.logRequest(getCurrentUser(), request, response);
    }

    @Override
    public void logSave(Object entity) {
        if (entity instanceof Comment) {
            commentLoggingService.logDeleting(getCurrentUser(), (Comment) entity);
        }

    }

    @Override
    public void logDeleting(Object entity) {
        if (entity instanceof Comment) {
            commentLoggingService.logDeleting(getCurrentUser(), (Comment) entity);
        }

    }


    private User getCurrentUser() {
        AuthUserDetails currentUserDetails = authenticationFacade.getCurrentUserFromContext();
        if (currentUserDetails == null) {
            return null;
        }

        return userRepository.findById(currentUserDetails.getUserId()).orElse(null);
    }
    
}
