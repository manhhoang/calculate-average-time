package com.mh.exception;

public class ApiException extends RuntimeException {

    public static final String SAVE_ERROR_CODE = "100";
    public static final String SAVE_ERROR_MESSAGE = "Fail to save new task";

    private String errorCode;
    private String errorMessage;

    public ApiException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
