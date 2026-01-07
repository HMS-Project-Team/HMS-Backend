package com.example.hms.HMS.controllers;

import com.example.hms.HMS.dtos.requests.BulkPrivilegeAssignmentDto;
import com.example.hms.HMS.annotations.RequirePrivilege;
import com.example.hms.HMS.dtos.requests.HotelPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.HotelPrivilegeResponseDto;
import com.example.hms.HMS.enums.RestApiResponseStatusCodes;
import com.example.hms.HMS.services.HotelPrivilegeService;
import com.example.hms.HMS.utils.ResponseWrapper;
import com.example.hms.HMS.utils.EndpointBundle;
import com.example.hms.HMS.enums.PrivilegeType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.HOTEL_PRIVILEGES)
@RequiredArgsConstructor
public class SettingsHotelPrivilegeController {

        private final HotelPrivilegeService hotelPrivilegeService;


}
