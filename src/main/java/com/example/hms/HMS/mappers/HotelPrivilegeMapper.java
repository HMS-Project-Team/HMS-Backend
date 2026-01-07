package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.HotelPrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.HotelPrivilegeResponseDto;
import com.example.hms.HMS.entities.HotelPrivilege;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelPrivilegeMapper {
    HotelPrivilege toEntity(HotelPrivilegeRequestDto dto);

    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "hotelName", source = "hotel.hotelName")
    @Mapping(target = "privilegeId", source = "privilege.id")
    @Mapping(target = "privilegeName", source = "privilege.name")
    @Mapping(target = "pagePath", source = "privilege.pagePath")
    HotelPrivilegeResponseDto toDto(HotelPrivilege entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HotelPrivilegeRequestDto dto, @MappingTarget HotelPrivilege entity);
}
