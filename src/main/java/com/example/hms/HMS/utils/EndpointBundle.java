package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";

    //Settings
    public static final String SETTINGS = BASE_URL + "/settings";

    //Roles
    public static final String ROLES = "/roles";
    public static final String ROLES_BY_ID = ROLES + ID;
    public static final String ROLES_BY_HOTEL = "/{hotelId}" + ROLES;
}