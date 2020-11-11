package com.mori5.itsecurity.errorhandling.exception;


import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class CredentialException extends ItSecurityException {
    public CredentialException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }
}
