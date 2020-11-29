package com.mori5.itsecurity.logging.service.impl;

import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.logging.domain.AuthLog;
import com.mori5.itsecurity.logging.repository.AuthLogRepository;
import com.mori5.itsecurity.logging.service.AuthLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthLoggingServiceImpl implements AuthLoggingService {

    private final AuthLogRepository authLogRepository;

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
}
