package com.sahibinden_clone.sahibinden_clone.dto;

import com.sahibinden_clone.sahibinden_clone.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
