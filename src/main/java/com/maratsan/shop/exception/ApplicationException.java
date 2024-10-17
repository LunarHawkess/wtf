package com.maratsan.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
abstract class ApplicationException extends RuntimeException {

    private final HttpStatus httpCode;
    private final Enum<?> errorCode;

    protected ApplicationException(String message, HttpStatus httpCode, Enum<?> errorCode) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    protected ApplicationException(String message, HttpStatus httpCode, Enum<?> errorCode, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

}
