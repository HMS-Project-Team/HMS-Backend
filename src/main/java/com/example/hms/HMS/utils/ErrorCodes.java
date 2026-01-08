package com.example.hms.HMS.utils;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:ErrorMessages.properties")
public class ErrorCodes {

    @Value("${validation.badRequest}")
    private String badRequest;

    @Value("${validation.alreadyExist}")
    private String alreadyExist;

    @Value("${validation.notFound}")
    private String notFound;

    @Value("${validation.notValid}")
    private String notValid;

    @Value("${validation.missingField}")
    private String missingField;

    @Value("${validation.typeMismatch}")
    private String typeMismatch;

    @Value("${validation.methodNotAllowed}")
    private String methodNotAllowed;

    @Value("${validation.invalidFormat}")
    private String invalidFormat;

    @Value("${validation.invalidId}")
    private String invalidId;

    @Value("${validation.constraintViolation}")
    private String constraintViolation;

    @Value("${validation.unprocessableEntity}")
    private String unprocessableEntity;

    @Value("${validation.paginationInvalid}")
    private String paginationInvalid;

    @Value("${auth.unauthorized}")
    private String unauthorized;

    @Value("${auth.tokenInvalid}")
    private String tokenInvalid;

    @Value("${auth.tokenExpired}")
    private String tokenExpired;

    @Value("${auth.forbidden}")
    private String forbidden;

    @Value("${auth.insufficientPermissions}")
    private String insufficientPermissions;

    @Value("${resource.conflict}")
    private String conflict;

    @Value("${resource.foreignKeyViolation}")
    private String foreignKeyViolation;

    @Value("${resource.duplicateEntry}")
    private String duplicateEntry;

    @Value("${resource.operationNotAllowed}")
    private String operationNotAllowed;
}
