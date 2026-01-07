package com.example.hms.HMS.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkPrivilegeAssignmentDto {
    private List<Long> privilegeIds;
    private List<HotelPrivilegeRequestDto> hotelPrivileges;
    private List<RolePrivilegeRequestDto> rolePrivileges;
    private List<UserPrivilegeRequestDto> userPrivileges;
}
