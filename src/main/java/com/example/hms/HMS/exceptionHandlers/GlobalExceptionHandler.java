package com.example.hms.HMS.exceptionHandlers;

import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.utils.ErrorCodes;
import com.example.hms.HMS.utils.ErrorDetail;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.ValidationMessages;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {


    @Autowired
    ErrorCodes errorCodes;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<?>> handleResourceNotFoundException(ResourceNotFoundException e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());
        errorDetails.add(errorDetail);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(

                RestApiResponseStatusCodes.NOT_FOUND.getCode(),
                ValidationMessages.NOT_FOUND,
                errorDetails
        ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleDataIntegrityViolation(DataIntegrityViolationException e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();

        String message;

        // Identify duplicate entry or foreign key violation
        if (e.getMessage() != null && e.getMessage().toLowerCase().contains("foreign key")) {
            message = ValidationMessages.FOREIGN_KEY_CONSTRAINT;
            errorDetails.add(new ErrorDetail(
                    new Date(),
                    message,
                    errorCodes.getAlreadyExist()
            ));
        } else {
            message = ValidationMessages.DUPLICATE_ENTRY;
            errorDetails.add(new ErrorDetail(
                    new Date(),
                    message,
                    errorCodes.getAlreadyExist()
            ));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.CONFLICT.getCode(),
                        message,
                        errorDetails
                )
        );
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<?>> handleValidation(MethodArgumentNotValidException ex) {

        List<ErrorDetail> errorDetails = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String errorMessage = error.getField() + ": " + error.getDefaultMessage();
                    ErrorDetail detail = new ErrorDetail(
                            new Date(),
                            errorMessage,
                            errorCodes.getAlreadyExist()
                    );
                    errorDetails.add(detail);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.VALIDATION_FAILED.getCode(),
                        ValidationMessages.VALIDATION_FAILED,
                        errorDetails
                ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseWrapper<?>> handleNoSuchElementException(NoSuchElementException e) {

        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper<>(RestApiResponseStatusCodes.NOT_FOUND.getCode(), ValidationMessages.INVALID_ID, errorDetail));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseWrapper<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());
        errorDetails.add(errorDetail);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.WRONG_API_CALL,
                        errorDetails
                )
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<?>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());
        errorDetails.add(errorDetail);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.BAD_REQUEST,
                        errorDetails
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<?>> handleConstraintViolation(ConstraintViolationException e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();

        e.getConstraintViolations().forEach(v -> {
            String message = v.getPropertyPath() + ": " + v.getMessage();
            errorDetails.add(new ErrorDetail(
                    new Date(),
                    message,
                    errorCodes.getNotValid()
            ));
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>(
                        RestApiResponseStatusCodes.VALIDATION_FAILED.getCode(),
                        ValidationMessages.VALIDATION_FAILED,
                        errorDetails
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());
        errorDetails.add(errorDetail);
        ResponseWrapper<List<ErrorDetail>> response = new ResponseWrapper<>(
                RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                ValidationMessages.INVALID_INPUT,
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<?>> handleException(
            Exception e) {

        List<ErrorDetail> errorDetails = new ArrayList<>();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), errorCodes.getNotFound());
        errorDetails.add(errorDetail);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseWrapper<>(
                        RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
                        ValidationMessages.BAD_REQUEST,
                        errorDetails
                )
        );
    }




}
