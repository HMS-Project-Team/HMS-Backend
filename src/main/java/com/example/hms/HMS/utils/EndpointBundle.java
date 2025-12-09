package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";
    public static final String SETTINGS = BASE_URL +"/settings";
    public static final String HOTEL = SETTINGS+"/hotel";
    public static final String CREATE_HOTEL = "/add";


    //Settings
    public static final String SETTINGS = BASE_URL+"/settings";

    //Roles
    public static final String ROLES = "/roles";
    public static final String ROLES_BY_ID = ROLES + ID;
    public static final String ROLES_BY_HOTEL = "/{hotelId}" + ROLES;

    public static final String HOTEL_ID = "/{hotelId}";
    public static final String CREATE_ROLE = HOTEL_ID+ROLES+"/add";


    //Settings
    public static final String SETTINGS = BASE_URL+"/settings";

    //Users
    public static final String USERS = "/users";
    public static final String USERS_BY_ID = USERS + ID;
    public static final String HOTEL_ID = "/{hotelId}";
    public static final String GET_ALL_USERS =HOTEL_ID+USERS;
    public static final String CREATE_ROLE = HOTEL_ID + USERS + "/add";
}
