package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.RoomTypeRequestDto;
import com.example.hms.HMS.dtos.responses.RoomTypeResponseDto;
import com.example.hms.HMS.entities.Amenities;
import com.example.hms.HMS.entities.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AmenitiesMapper.class })
public interface RoomTypeMapper {
    @Mapping(target = "amenities", ignore = true)
    RoomType toEntity(RoomTypeRequestDto roomTypeRequestDto);
    @Mapping(target = "amenityIds", source = "amenities")
    RoomTypeResponseDto toDto(RoomType roomType);

    default List<Long> mapAmenitiesToIds(List<Amenities> amenities) {
        if (amenities == null) {
            return null;
        }
        return amenities.stream()
                .map(Amenities::getId)
                .toList();
    }

    List<RoomTypeResponseDto> toDtoList(List<RoomType> roomTypeList);
}
