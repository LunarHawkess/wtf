package com.maratsan.shop.exception;

import com.maratsan.shop.common.DefaultErrorCode;
import org.springframework.http.HttpStatus;

public class SystemException extends ApplicationException {

    public SystemException(String message) {
        this(message, DefaultErrorCode.INTERNAL_SERVER_ERROR);
    }

    public SystemException(String message, Throwable cause) {
        this(message, DefaultErrorCode.INTERNAL_SERVER_ERROR, cause);
    }

    public SystemException(String message, Enum<?> errorCode) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public SystemException(String message, Enum<?> errorCode, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, errorCode, cause);
    }

    protected SystemException(String message, HttpStatus httpCode, Enum<?> errorCode) {
        super(message, httpCode, errorCode);
    }

    protected SystemException(String message, HttpStatus httpCode, Enum<?> errorCode, Throwable cause) {
        super(message, httpCode, errorCode, cause);
    }

}
