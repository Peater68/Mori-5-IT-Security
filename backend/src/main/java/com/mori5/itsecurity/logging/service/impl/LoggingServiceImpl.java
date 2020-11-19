package com.mori5.itsecurity.logging.service.impl;

import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.domain.AuthLog;
import com.mori5.itsecurity.logging.repository.AuthLogRepository;
import com.mori5.itsecurity.logging.service.*;
import com.mori5.itsecurity.repository.UserRepository;
import com.mori5.itsecurity.security.AuthUserDetails;
import com.mori5.itsecurity.security.AuthenticationFacade;
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
    private final DocumentLoggingService documentLoggingService;
    private final UserLoggingService userLoggingService;
    private final AuthLogRepository authLogRepository;

    @Override
    public void logRequest(HttpServletRequest request, HttpServletResponse response) {
        requestLoggingService.logRequest(getCurrentUser(), request, response);
    }

    @Override
    public void logSave(Object entity) {
        if (entity instanceof Comment) {
            commentLoggingService.logSave(getCurrentUser(), (Comment) entity);
        } else if (entity instanceof Document) {
            documentLoggingService.logSave(getCurrentUser(), (Document) entity);
        } else if (entity instanceof User) {
            userLoggingService.logSave(getCurrentUser(), (User) entity);
        }
    }

    @Override
    public void logDeleting(Object entity) {
        if (entity instanceof Comment) {
            commentLoggingService.logDeleting(getCurrentUser(), (Comment) entity);
        } else if (entity instanceof Document) {
            documentLoggingService.logDeleting(getCurrentUser(), (Document) entity);
        } else if (entity instanceof User) {
            userLoggingService.logDeleting(getCurrentUser(), (User) entity);
        }
    }

    @Override
    public void logLogin(User actor, Boolean success) {
        authLogRepository.save(
                AuthLog.builder()
                        .actor(actor)
                        .operation("LOGIN")
                        .success(success)
                        .build()
        );
    }

    @Override
    public void logRenew(User actor, Boolean success) {
        authLogRepository.save(
                AuthLog.builder()
                        .actor(actor)
                        .operation("RENEW")
                        .success(success)
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
