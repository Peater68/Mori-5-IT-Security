package com.mori5.itsecurity.logging.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {

    void logRequest(HttpServletRequest request, HttpServletResponse response);

    void logSave(Object entity);

    void logDeleting(Object entity);

}
