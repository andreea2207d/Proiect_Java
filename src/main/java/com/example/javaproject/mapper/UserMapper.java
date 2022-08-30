package com.example.javaproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.javaproject.model.User;
import com.example.javaproject.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", source = "userInfo.address")
    @Mapping(target = "phoneNumber", source = "userInfo.phoneNumber")
    @Mapping(target = "birthDate", source = "userInfo.birthDate")
    UserDto mapToDto(User user);

    @Mapping(target = "userInfo.address", source = "address")
    @Mapping(target = "userInfo.phoneNumber", source = "phoneNumber")
    @Mapping(target = "userInfo.birthDate", source = "birthDate")
    User mapToEntity(UserDto user);
}
