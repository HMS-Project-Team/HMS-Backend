package com.example.hms.HMS.utils;

public class ValidationMessages {

    // ==== Success ====
    public static final String SUCCESS = "Success";
    public static final String SAVED_SUCCESSFULLY = "Saved successfully.";
    public static final String UPDATED_SUCCESSFULLY = "Updated successfully.";
    public static final String DELETED_SUCCESSFULLY = "Deleted successfully.";
    public static final String RETRIEVED_SUCCESSFULLY = "Retrieved successfully.";

    // ==== Failure ====
    public static final String SAVE_FAILED = "Save failed.";
    public static final String UPDATE_FAILED = "Update failed.";
    public static final String DELETE_FAILED = "Delete failed.";
    public static final String RETRIEVE_FAILED = "Retrieve failed.";

    // ==== Common Validation ====
    public static final String INVALID_ID = "Invalid ID.";
    public static final String INVALID_INPUT = "Invalid input data.";
    public static final String INVALID_FORMAT = "Input format is incorrect.";
    public static final String REQUIRED_FIELD_MISSING = "Required field is missing.";
    public static final String MINIMUM_REQUIREMENT = "At least one field must be present.";
    public static final String BAD_REQUEST = "Bad request.";
    public static final String METHOD_NOT_ALLOWED = "Method not allowed.";
    public static final String VALIDATION_FAILED = "Validation Failed";

    // ==== Authentication ====
    public static final String UNAUTHORIZED = "You are not authorized to perform this action.";
    public static final String INVALID_CREDENTIALS = "Invalid email or password.";
    public static final String ACCESS_REVOKED="Token is already invalidated or logged out";
    public static final String TOKEN_EXPIRED="The token has expired";
    public static final String TOKEN_NOTFOUND="The token not found";




    // ==== Email & Password ====
    public static final String EMAIL_REQUIRED = "Email is required.";
    public static final String INVALID_EMAIL = "An email should be in the form of: your-email@example.com.";
    public static final String PASSWORD_REQUIRED = "Password is required and must be at least 8 characters.";
    public static final String PASSWORD_MIN_LENGTH = "Password must be at least 8 characters.";
    public static final String PASSWORD_COMPLEXITY = "Password must contain uppercase, lowercase, number, and special character";
    public static final String PASSWORD_MISMATCH = "New password and confirm password do not match";
    public static final String NEW_PASSWORD_SAME_AS_OLD = "New password cannot be the same as the old password";

    public static final String NIC_REQUIRED = "NIC is required.";
    public static final String NIC_VALIDATION = "NIC must be 9 digits + V/X or 12 digits.";

    // ==== OTP ====
    public static final String OTP_NOT_FOUND = "OTP not found.";
    public static final String INVALID_OTP = "Invalid OTP.";
    public static final String MISSING_PARAMETER = "Missing parameter.";
    public static final String MISSING_PATHVARIABLE = "Missing pathvariable.";

    // ==== Errors ====
    public static final String NOT_FOUND = "Resource not found.";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
    public static final String DUPLICATE_ENTRY = "Duplicate entry.";
    public static final String FOREIGN_KEY_CONSTRAINT = "Cannot delete: This record is linked to another record.";
    public static final String WRONG_API_CALL = "Incorrect API path or method.";
    public static final String INVALID_JSON = "Invalid JSON format.";


    // ==== Otp ====
    public static final String OTP_SEND_SUCCESSFUL = "OTP Sent Successfully";
    public static final String OTP_NOTNULL= "OTP or email missing/invalid";
    public static final String OTP_SIZE= "Maximum Number of OTP is 6 Digits";
    public static final String OTP_REVOKED= "OTP Already Revoked";
    public static final String OTP_EXPIRED= "OTP validity period has expired";
    public static final String OTP_MISSMATCHED= "Incorrect OTP";
    public static final String OTP_VERIFIED= "OTP Verified Successfully";

    //==== User ====
    public static final String ROLES_NOT_BELONG_TO_HOTEL = "One or more roles do not belong to this hotel.";
    public static final String USER_HAS_NO_ROLES = "User has no roles assigned.";
    public static final String ROLE_NOT_LINKED_TO_HOTEL = "User's role is not linked to any hotel.";


    public static final String INVALID_PAGE_SIZE_MSG = "Invalid Page Size.";
}
