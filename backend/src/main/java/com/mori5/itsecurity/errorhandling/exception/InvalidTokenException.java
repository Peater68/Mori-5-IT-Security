package com.mori5.itsecurity.errorhandling.exception;

import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class InvalidTokenException extends ItSecurityException {

    public InvalidTokenException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }

    public InvalidTokenException(Throwable e) {
        super(e);
    }
}
