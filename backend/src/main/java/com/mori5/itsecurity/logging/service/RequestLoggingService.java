package com.mori5.itsecurity.logging.service;

import com.mori5.itsecurity.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestLoggingService {

    void logRequest(User actor, HttpServletRequest request, HttpServletResponse response);

}
