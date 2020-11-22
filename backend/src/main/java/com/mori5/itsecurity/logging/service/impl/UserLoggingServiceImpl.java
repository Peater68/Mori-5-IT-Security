package com.mori5.itsecurity.logging.service.impl;

import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.domain.UserLog;
import com.mori5.itsecurity.logging.repository.UserLogRepository;
import com.mori5.itsecurity.logging.service.UserLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggingServiceImpl implements UserLoggingService {

    private final UserLogRepository userLogRepository;

    @Override
    public void logDeleting(User actor, User entity) {
        userLogRepository.save(
                UserLog.builder()
                        .actor(actor)
                        .actorRole(actor != null ? actor.getRole() : null)
                        .operation("DELETE")
                        .profileOf(entity)
                        .build()
        );
    }

    @Override
    public void logSave(User actor, User entity) {
        userLogRepository.save(
                UserLog.builder()
                        .actor(actor)
                        .actorRole(actor != null ? actor.getRole() : null)
                        .operation("SAVE")
                        .profileOf(entity)
                        .build()
        );
    }

}
