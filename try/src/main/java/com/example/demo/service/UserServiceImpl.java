package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /*
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User saveUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(saveUser.getId());
        userResponse.setName(saveUser.getName());
        userResponse.setEmail(saveUser.getEmail());

        return userResponse;
    }
     */

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = modelMapper.map(userRequest,User.class);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser,UserResponse.class);
    }

    /*
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> responseList = users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                        )).toList();
        return responseList;
    }
     */

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return  users.stream()
                .map(user->modelMapper.map(user,UserResponse.class))
                .toList();
    }

    /*
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

     */

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));

        return modelMapper.map(user,UserResponse.class);
    }

    /*
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User updateUser = userRepository.save(user);

        return new UserResponse(
                updateUser.getId(),
                updateUser.getName(),
                updateUser.getEmail()
        );
    }
     */

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));

        modelMapper.map(userRequest,user);

        User updateUser = userRepository.save(user);

        return modelMapper.map(updateUser,UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));

        userRepository.delete(user);
    }
}
