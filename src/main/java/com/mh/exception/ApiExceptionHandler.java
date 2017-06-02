package com.mh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApiException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleControllerException(ApiException apiException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(apiException.getErrorCode());
        errorResponse.setErrorMessage(apiException.getErrorMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
