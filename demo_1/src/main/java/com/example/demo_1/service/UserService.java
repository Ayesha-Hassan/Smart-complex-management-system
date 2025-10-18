package com.example.demo_1.service;

import com.example.demo_1.dto.UserDto;
import com.example.demo_1.mapper.UserMapper;
import com.example.demo_1.model.Roles;
import com.example.demo_1.model.User;
import com.example.demo_1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public List<UserDto> ViewAll() {
        return userRepository.findAll(Sort.by("role")).stream().map(userMapper::toDto).toList();
    }
    public UserDto SpecificUser(Long id){
        User user= userRepository.findById(id).orElseThrow();
        return userMapper.toDto(user);
    }
}
