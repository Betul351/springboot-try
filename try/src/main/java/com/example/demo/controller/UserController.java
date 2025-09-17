package com.example.demo.controller;


import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/create")
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest){
        logger.info("POST /create çağırıldı. Kullanıcı adı: {}", userRequest.getName());
        return userService.createUser(userRequest);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        logger.debug("GET /{id} çağırıldı. ID: {}",id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@Valid @PathVariable Long id, @RequestBody UserRequest userRequest){
        logger.info("PUT /{id} çağırıldı. ID:{}, Yeni ad: {}",id,userRequest.getName() );
        return userService.updateUser(id,userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}
