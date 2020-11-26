package com.mori5.itsecurity.logging.service;

import com.mori5.itsecurity.domain.User;

public interface AuthLoggingService {

    void logLogin(User actor, Boolean success);

    void logRenew(User actor, Boolean success);

}
