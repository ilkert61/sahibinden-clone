package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUsers")
    public Users addUser(@RequestBody UserDTO UserDTO) throws Exception {
        return userService.addUser(UserDTO);
    }

}
