package com.maratsan.shop.handler;

import com.maratsan.shop.common.DefaultErrorCode;
import com.maratsan.shop.config.ErrorHandlingConfig;
import com.maratsan.shop.exception.BusinessException;
import com.maratsan.shop.exception.SystemException;
import com.maratsan.shop.response.ErrorResponse;
import com.maratsan.shop.response.ValidationErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorHandlingConfig errorHandlingConfig;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.BUSINESS)) {
            log.error(ex.getMessage(), ex);
        }

        var errorResponse = new ErrorResponse(
                ex.getErrorCode().name(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpCode());
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException ex) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.SYSTEM)) {
            log.error(ex.getMessage(), ex);
        }

        var errorResponse = new ErrorResponse(
                ex.getErrorCode().name(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.BUSINESS)) {
            log.error(ex.getMessage(), ex);
        }

        var violations = ex.getConstraintViolations().stream()
                .map(violation ->
                        new ValidationErrorResponse.Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                ).toList();

        var validationErrorResponse = new ValidationErrorResponse(
                DefaultErrorCode.VALIDATION_ERROR.name(),
                ex.getMessage(),
                violations
        );

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException ex) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.BUSINESS)) {
            log.error(ex.getMessage(), ex);
        }

        var errorResponse = new ErrorResponse(
                DefaultErrorCode.FORBIDDEN.name(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable ex) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.SYSTEM)) {
            log.error(ex.getMessage(), ex);
        }

        var errorResponse = new ErrorResponse(
                DefaultErrorCode.INTERNAL_SERVER_ERROR.name(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (statusCode.is4xxClientError()) {
            if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.BUSINESS)) {
                log.error(ex.getMessage(), ex);
            }
        } else if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.SYSTEM)) {
            log.error(ex.getMessage(), ex);
        }

        var errorCode = statusCode.is4xxClientError()
                ? DefaultErrorCode.BAD_REQUEST
                : DefaultErrorCode.INTERNAL_SERVER_ERROR;

        var errorResponse = new ErrorResponse(
                errorCode.name(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (errorHandlingConfig.isLoggingEnabled(ErrorHandlingConfig.LoggingLevel.BUSINESS)) {
            log.error(ex.getMessage(), ex);
        }

        var violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error ->
                        new ValidationErrorResponse.Violation(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                ).toList();

        var validationErrorResponse = new ValidationErrorResponse(
                DefaultErrorCode.VALIDATION_ERROR.name(),
                ex.getMessage(),
                violations
        );

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
