package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.PoliciesRequestDto;
import com.example.hms.HMS.dtos.responses.PoliciesResponseDto;
import com.example.hms.HMS.entities.Policies;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PoliciesMapper {
    Policies toEntity(PoliciesRequestDto policiesRequestDto);
    PoliciesResponseDto toDto(Policies policies);
    List<PoliciesResponseDto> toDtoList(List<Policies> policiesList);
}
