package com.mori5.itsecurity.errorhandling;

import com.mori5.itsecurity.errorhandling.domain.ErrorResponseDTO;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(UserIsBannedException.class)
    public Object handleUserIsBannedException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ItSecurityErrors.USER_BANNED, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OperationFailedException.class)
    public Object handleOperationFailedException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ItSecurityErrors.FAILED_OPERATION, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDeniedException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ItSecurityErrors.ACCESS_DENIED, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public Object handleConflictException(ItSecurityException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getError(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CredentialException.class, NoUserInContextException.class})
    public Object handleCredentialException(ItSecurityException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getError(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFoundException(ItSecurityException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getError(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public Object handleInvalidOperationException(ItSecurityException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getError(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public Object handleInvalidTokenException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ItSecurityErrors.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public Object handleUnprocessableEntityException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ItSecurityErrors.UNPROCESSABLE_ENTITY, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
