package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID = "/{id}";
    public static final String SEARCH = "/search";
    public static final String SETTINGS = BASE_URL +"/settings";
    public static final String ROOM = BASE_URL +"/room";
    public static final String HOTEL = SETTINGS+"/hotel";
    public static final String CREATE_HOTEL = "/add";


    //Settings

    //Roles
    public static final String ROLES = "/roles";
    public static final String ROLES_BY_ID = ROLES + ID;
    public static final String ROLES_BY_HOTEL = "/{hotelId}" + ROLES;

    public static final String HOTEL_ID = "/{hotelId}";
    public static final String CREATE_ROLE = HOTEL_ID + ROLES + "/add";

    //RoomArea
    public static final String ROOMAREA = ROOM + "/roomarea";
    public static final String CREATE_ROOMAREA = "/add";


    //Users
    public static final String USERS = "/users";
    public static final String USERS_BY_ID = USERS + ID;
    public static final String GET_ALL_USERS = HOTEL_ID + USERS;
    public static final String CREATE_USER = HOTEL_ID + USERS + "/add";
    //Login
    public static final String LOGIN = "/login";
    public static final String AUTH = BASE_URL + "/auth";
    public static final String OTP = "/otp";
    public static final String VERIFY_OTP = OTP + "/verify";
    public static final String NEW_PASSWORD = "/new-Password";
    public static final String LOGOUT="/logout";



}
