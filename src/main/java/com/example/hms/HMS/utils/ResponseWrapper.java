package com.example.hms.HMS.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
    private int statusCode;
    private String statusMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


}
