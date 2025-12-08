package com.example.hms.HMS.utils;

public final class  EndpointBundle {
    public static final String BASE_URL = "/api/v1";
    public static final String ID ="/{id}";
    public static final String SEARCH = "/search";

    public static final String SETTINGS = BASE_URL + "/settings";
    public static final String HOTEL_ID = "/{hotelId}";
    public static final String ROLE = "/roles";
    public static final String CREATE_ROLE = HOTEL_ID+ROLE+"/add";


}
