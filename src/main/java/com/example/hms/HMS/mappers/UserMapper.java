package com.example.hms.HMS.mappers;

import com.example.hms.HMS.dtos.requests.UserRequestDto;
import com.example.hms.HMS.dtos.responses.UserResponseDto;
import com.example.hms.HMS.entities.Role;
import com.example.hms.HMS.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "roles", source = "roles",ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    UserResponseDto toResponseDto(User user);

    @Mapping(target = "hotelId", expression = "java(user.getRoles() != null && !user.getRoles().isEmpty() ? user.getRoles().get(0).getHotel().getId() : null)")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToIds")
    UserResponseDto toDto(User user);

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


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(UserRequestDto dto, @MappingTarget User user);


    @Named("rolesToIds")
    default List<Long> rolesToIds(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }


}
