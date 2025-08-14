package com.sahibinden_clone.sahibinden_clone.dto;

import com.sahibinden_clone.sahibinden_clone.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private Gender gender;
    private LocalDate birthday;

}
