package com.maratsan.shop.exception;

import com.maratsan.shop.common.DefaultErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessException extends ApplicationException {

    public BusinessException(String message) {
        this(message, DefaultErrorCode.BAD_REQUEST);
    }

    public BusinessException(String message, Throwable cause) {
        this(message, DefaultErrorCode.BAD_REQUEST, cause);
    }

    public BusinessException(String message, Enum<?> errorCode) {
        super(message, HttpStatus.BAD_REQUEST, errorCode);
    }

    public BusinessException(String message, Enum<?> errorCode, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, errorCode, cause);
    }

    protected BusinessException(String message, HttpStatus httpCode, Enum<?> errorCode) {
        super(message, httpCode, errorCode);
    }

    protected BusinessException(String message, HttpStatus httpCode, Enum<?> errorCode, Throwable cause) {
        super(message, httpCode, errorCode, cause);
    }

}
