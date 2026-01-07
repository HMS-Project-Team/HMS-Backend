package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RolePrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.RolePrivilegeResponseDto;
import com.example.hms.HMS.entities.RoleHotelPrivilege;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolePrivilegeMapper {
    RoleHotelPrivilege toEntity(RolePrivilegeRequestDto dto);

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "hotelPrivilegeId", source = "hotelPrivilege.id")
    @Mapping(target = "privilegeName", source = "hotelPrivilege.privilege.name")
    @Mapping(target = "pagePath", source = "hotelPrivilege.privilege.pagePath")
    RolePrivilegeResponseDto toDto(RoleHotelPrivilege entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(RolePrivilegeRequestDto dto, @MappingTarget RoleHotelPrivilege entity);
}
