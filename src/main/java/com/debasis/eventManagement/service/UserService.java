package com.debasis.eventManagement.service;

import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.request.LoginRequest;
import com.debasis.eventManagement.request.UserDTO;
import com.debasis.eventManagement.response.Response;

import java.util.List;

public interface UserService {
    Response register(UserDTO user);

    Response login(LoginRequest loginRequest);

    List<User> getAllUsers();

    Response getUserById(Long userId);
    Response deleteUser(Long userId);
    Response getInfo(String email);

//    Response getAllUserEvents(Long userId);


    User updateUser(Long id, User userDetails);
}
