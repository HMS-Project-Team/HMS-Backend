package com.example.hms.HMS.mappers;


import com.example.hms.HMS.dtos.responses.RoleResponseDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "hotelId", expression = "java(user.getRoles() != null && !user.getRoles().isEmpty() ? user.getRoles().get(0).getHotel().getId() : null)")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToIds")
    UserResponseDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserResponseDto userDto);

    @Named("rolesToIds")
    default List<Long> rolesToIds(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }

    List<UserResponseDto> toDtoList(List<User> users);
}
