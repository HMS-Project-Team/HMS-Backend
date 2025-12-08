package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";

    //Settings
    public static final String SETTINGS = BASE_URL+"/settings";

    //Users
    public static final String USERS = "/users";
    public static final String USERS_BY_ID = USERS + ID;
}
