package com.maratsan.shop.exception;

import com.maratsan.shop.common.DefaultErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        this(message, DefaultErrorCode.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        this(message, DefaultErrorCode.NOT_FOUND, cause);
    }

    public NotFoundException(String message, Enum<?> errorCode) {
        super(message, HttpStatus.NOT_FOUND, errorCode);
    }

    public NotFoundException(String message, Enum<?> errorCode, Throwable cause) {
        super(message, HttpStatus.NOT_FOUND, errorCode, cause);
    }

}
