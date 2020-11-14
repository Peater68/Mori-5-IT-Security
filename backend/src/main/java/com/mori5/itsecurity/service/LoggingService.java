package com.mori5.itsecurity.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggingService {

    void logRequest(HttpServletRequest request, HttpServletResponse response);

}
