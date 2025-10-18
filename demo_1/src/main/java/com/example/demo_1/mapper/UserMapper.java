package com.example.demo_1.mapper;

import com.example.demo_1.dto.UserDto;
import com.example.demo_1.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
