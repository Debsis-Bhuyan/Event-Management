package com.debasis.eventManagement.serviceImpl;

import com.debasis.eventManagement.exception.UserException;
import com.debasis.eventManagement.model.Event;
import com.debasis.eventManagement.model.User;
import com.debasis.eventManagement.repository.EventRepository;
import com.debasis.eventManagement.repository.UserRepository;
import com.debasis.eventManagement.request.LoginRequest;
import com.debasis.eventManagement.request.UserDTO;
import com.debasis.eventManagement.response.EventResponse;
import com.debasis.eventManagement.response.Response;
import com.debasis.eventManagement.service.UserService;
import com.debasis.eventManagement.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    final private UserRepository userRepository;
    final private EventRepository eventRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Response register(UserDTO user) {
        Response response = new Response();
        try {
            boolean isExist = userRepository.existsByEmail(user.getEmail());
            if (isExist){
                throw new UserException(user.getEmail()+" Already exist in our System please Login.");
            }

            User newUser = userRepository.save(Utils.mapUserDTOToUserEntity(user));
            UserDTO userDTO= Utils.mapUserEntityToUserDTO(newUser);
            response.setMessage("User created successfully");
            response.setStatusCode(200);
            response.setUser(userDTO);


        }
        catch (UserException e){
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch (Exception e){
            response.setMessage("Error occurred during User Registration "+e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            boolean isUserExist = userRepository.existsByEmail(loginRequest.getEmail());

            if(isUserExist){
                Optional<User> user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
                if (user.isPresent()){
                    User userData = user.get();
                    UserDTO userdto = Utils.mapUserEntityToUserDTO(userData);response.setStatusCode(200);
                    response.setMessage("Login Successful");
                    response.setStatusCode(200);
                    response.setUser(userdto);
                }else{
                    throw new UserException("Login Unsuccessful Please check your emailId and Password.");

                }

            }
            else{
                throw new UserException(loginRequest.getEmail()+" is not exist in our System please Register.");

            }


        }
        catch (UserException e){
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch (Exception e){
            response.setMessage("Error occurred during User Login "+e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Response getUserById(Long userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("User data get Successfully");
            response.setUser(userDTO);

        } catch (UserException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting  users Details " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(Long userId) {
        Response response = new Response();

        try {
            userRepository.findById(userId).orElseThrow(() -> new UserException("User Not Found"));
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("User deleted successful");

        } catch (UserException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (UserException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }



    @Override
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User existUser =user.get();
            existUser.setFullName(userDetails.getFullName());
             return  userRepository.save(existUser);
        }
        return null;
    }
}
