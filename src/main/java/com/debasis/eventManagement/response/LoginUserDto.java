package com.debasis.eventManagement.response;


import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}
