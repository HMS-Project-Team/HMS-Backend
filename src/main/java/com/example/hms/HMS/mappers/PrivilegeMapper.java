package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.PrivilegeRequestDto;
import com.example.hms.HMS.dtos.responses.PrivilegeResponseDto;
import com.example.hms.HMS.entities.Privilege;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrivilegeMapper {
    Privilege toEntity(PrivilegeRequestDto dto);

    PrivilegeResponseDto toDto(Privilege entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PrivilegeRequestDto dto, @MappingTarget Privilege entity);
}
