package com.mori5.itsecurity.errorhandling;

import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class ItSecurityException extends RuntimeException {
    private ItSecurityErrors error;

    public ItSecurityException() {
    }

    public ItSecurityException(String message, ItSecurityErrors error) {
        super(message);
        this.error = error;
    }

    public ItSecurityException(String message, ItSecurityErrors error, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public ItSecurityErrors getError() {
        return this.error;
    }

    public ItSecurityException(String message) {
        super(message);
    }

    public ItSecurityException(ItSecurityErrors error) {
        this.error = error;
    }

    public ItSecurityException(Throwable e) {
        super(e);
    }

}
