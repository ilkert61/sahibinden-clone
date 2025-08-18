package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.dto.DeleteUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUsers")
    public Users addUser(@RequestBody UserDTO userDTO) throws Exception {
        return userService.addUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable UUID id, @RequestBody DeleteUserRequest request) {
        try {
            userService.deleteUserWithPassword(id, request.getPassword());
            return "Kullanıcı silindi.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
