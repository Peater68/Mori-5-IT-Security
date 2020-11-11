package com.mori5.itsecurity.errorhandling.domain;

import java.io.Serializable;

public class ErrorResponseDTO implements Serializable {

    private String errorMessage;
    private String errorKey;
    private Integer errorCode;

    public ErrorResponseDTO(ItSecurityErrors itsecurityError, String errorMessage) {
        this.errorCode = itsecurityError.getCode();
        this.errorKey = itsecurityError.getKey();
        this.errorMessage = errorMessage;
    }
}
