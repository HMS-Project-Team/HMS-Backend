package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.UserPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.UserPrivilegeResponseDto;
import com.example.hms.HMS.entities.UserPrivilege;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserPrivilegeMapper {
    UserPrivilege toEntity(UserPrivilegeRequestDto dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.firstname")
    @Mapping(target = "hotelPrivilegeId", source = "hotelPrivilege.id")
    @Mapping(target = "privilegeName", source = "hotelPrivilege.privilege.name")
    @Mapping(target = "pagePath", source = "hotelPrivilege.privilege.pagePath")
    UserPrivilegeResponseDto toDto(UserPrivilege entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserPrivilegeRequestDto dto, @MappingTarget UserPrivilege entity);
}
