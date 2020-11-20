package com.mori5.itsecurity.errorhandling.exception;

import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;

public class UserIsBannedException extends ItSecurityException {
    public UserIsBannedException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }
}
