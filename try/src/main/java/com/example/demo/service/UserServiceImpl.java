package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.LogService;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final LogService logService;

    @Autowired
    private PasswordEncoder passwordEncoder;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public UserResponse createUser(UserRequest userRequest) {
       logger.info("createUser metodu çağrıldı. Kullanıcı adı: {}", userRequest.getName());

        User user = modelMapper.map(userRequest,User.class);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        logService.logUserCreation(user.getName());
       // if (true) throw new RuntimeException("Kayıt hatası");

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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public UserResponse getUserById(Long id) {
        User user1 = userRepository.findById(id).orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // thread durumunu korumak için önerilir
            throw new RuntimeException("Thread kesintiye uğradı", e);
        }

        User user2 = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));

        System.out.println("İlk okuma: " + user1.getName());
        System.out.println("İkinci okuma: " + user2.getName());

        return modelMapper.map(user2,UserResponse.class);
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

    @Transactional
    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Kullanıcı bulunamadı"));

        modelMapper.map(userRequest,user);

        User updateUser = userRepository.save(user);

        return modelMapper.map(updateUser,UserResponse.class);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));

        userRepository.delete(user);
    }
}
