package com.mori5.itsecurity.errorhandling.exception;


import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class InvalidOperationException extends ItSecurityException {
    public InvalidOperationException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }
}
