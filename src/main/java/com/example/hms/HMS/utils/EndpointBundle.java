package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";
    public static final String AUTH = BASE_URL + "/auth";
    // Login
    public static final String LOGIN = "/login";

    public static final String NEW_PASSWORD = "/new-Password";

    //Login
    public static final String AUTH = BASE_URL + "/auth";
    public static final String OTP = "/otp";
    public static final String VERIFY_OTP = OTP + "/verify";

}
