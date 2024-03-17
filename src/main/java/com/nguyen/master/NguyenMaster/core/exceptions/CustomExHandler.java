package com.nguyen.master.NguyenMaster.core.exceptions;

import com.nguyen.master.NguyenMaster.core.BaseErrorResponse;
import com.nguyen.master.NguyenMaster.core.constant.Constants;
import com.nguyen.master.NguyenMaster.core.common.ErrorMessage;
import com.nguyen.master.NguyenMaster.core.constant.enums.ApiStatus;
import com.nguyen.master.NguyenMaster.core.exceptions.rest.*;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExHandler {
    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessages(e.getErrorMessages())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({UnknownException.class, Exception.class, SqlException.class, UsernameNotFoundException.class, UnsupportedJwtException.class})
    public ResponseEntity<?> handleResourceNotFound(Exception e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.UNKNOWN.getCode())
                .errorMessages(List.of(new ErrorMessage(ApiStatus.UNKNOWN.getCode(), e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.BAD_REQUEST.getCode())
                .errorMessages(List.of(new ErrorMessage(ApiStatus.BAD_REQUEST.getCode(), e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.RESOURCE_NOT_FOUND.getCode())
                .errorMessages(List.of(new ErrorMessage(ApiStatus.RESOURCE_NOT_FOUND.getCode(), e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            String fieldName = messageSource.getMessage(((FieldError) error).getField(), null, LocaleContextHolder.getLocale());
            ErrorMessage errorMessage = new ErrorMessage(fieldName, fieldName.concat(Constants.SPACE).concat(message));
            errorMessages.add(errorMessage);
        });
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.INPUT_ERROR.getCode())
                .errorMessages(errorMessages)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleFieldValidationExceptions(IllegalArgumentException e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.INPUT_ERROR.getCode())
                .errorMessages(List.of(new ErrorMessage(ApiStatus.INPUT_ERROR.getCode(), e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CatchException.class)
    public ResponseEntity<Object> handleCatchExceptions(CatchException e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(ApiStatus.INPUT_ERROR.getCode())
                .errorMessages(List.of(new ErrorMessage(ApiStatus.FAILED.getCode(), e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Error400Exception.class)
    public ResponseEntity<Object> handleError400ExceptionException(Error400Exception e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessages(e.getErrorMessages())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Error500Exception.class)
    public ResponseEntity<Object> handleError500ExceptionException(Error500Exception e) {
        BaseErrorResponse<?> errorResponse = BaseErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessages(e.getErrorMessages())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
