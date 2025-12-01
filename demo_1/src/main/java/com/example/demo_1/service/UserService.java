package com.example.demo_1.service;

import com.example.demo_1.dto.UserDto;
import com.example.demo_1.mapper.UserMapper;
import com.example.demo_1.model.Roles;
import com.example.demo_1.model.User;
import com.example.demo_1.repository.UserRepository;
import com.example.demo_1.requestObject.RegisterUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public List<UserDto> ViewAll() {
        return userRepository.findAll(Sort.by("role")).stream().map(userMapper::toDto).toList();
    }
    public UserDto SpecificUser(Long id){
        User user= userRepository.findById(id).orElseThrow();
        return userMapper.toDto(user);
    }
    public UserDto RegisterUser(RegisterUserRequest request){
        User user= userMapper.toEntity(request);
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }
    public UserDto getUserByEmail(String email) {
        User user= userRepository.findByEmail(email).orElseThrow();
        return userMapper.toDto(user);
    }
    
    public User getUserByEmailEntity(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    public List<UserDto> getUsersByRole(Roles role) {
        return userRepository.findByRole(role).stream().map(userMapper::toDto).toList();
    }
}
