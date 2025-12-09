package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.User;
import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    UserResponseDto toResponseDto(User user);

    default List<Role> map(List<Long> roleIds) {
        if (roleIds == null) return null;
        return roleIds.stream()
                .map(id -> {
                    Role role = new Role();
                    role.setId(id);
                    return role;
                })
                .collect(Collectors.toList());
    }

    default List<Long> mapToIds(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

}
