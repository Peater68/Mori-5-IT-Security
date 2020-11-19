package com.mori5.itsecurity.logging.service;

import com.mori5.itsecurity.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {

    void logRequest(HttpServletRequest request, HttpServletResponse response);

    void logSave(Object entity);

    void logDeleting(Object entity);

    void logLogin(User actor, Boolean success);

    void logRenew(User actor, Boolean success);

}
