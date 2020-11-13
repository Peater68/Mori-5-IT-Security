package com.mori5.itsecurity.errorhandling.exception;

import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class UnprocessableEntityException extends ItSecurityException {
    public UnprocessableEntityException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }
}

