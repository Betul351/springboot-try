package com.example.demo.service.impl;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id,UserRequest userRequest);
    void deleteUser(Long id);

}
