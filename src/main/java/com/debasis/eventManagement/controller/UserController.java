package com.debasis.eventManagement.controller;

import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.response.Response;
import com.debasis.eventManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Response> findUserById(@PathVariable Long id){
        Response response = userService.getUserById(id);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping( "/get-all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return  ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @GetMapping( "/findByEmail")
    public ResponseEntity<Response> getUserInfo(@RequestParam String email){
        Response response = userService.getInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        Response userResponse= userService.deleteUser(id);
        return ResponseEntity.status(userResponse.getStatusCode()).body(userResponse);
    }

}
