package com.mori5.itsecurity.errorhandling.exception;

import com.mori5.itsecurity.errorhandling.ItSecurityException;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;


public class FileUploadException extends ItSecurityException {
    public FileUploadException(String msg, ItSecurityErrors itSecurityError) {
        super(msg, itSecurityError);
    }
}