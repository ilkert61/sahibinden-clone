package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    public Users addUser(@RequestBody UserDTO userDTO) throws Exception {
        if (usersRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new Exception("Kullanıcı adı zaten alınmış.");
        } else {
            Users user = new Users();
            user.setUsername(userDTO.getUsername());
            user.setFirstname(userDTO.getFirstName());
            user.setLastname(userDTO.getLastName());
            user.setPhone(userDTO.getPhone());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setGender(userDTO.getGender());
            user.setBirthdate(userDTO.getBirthday());
            usersRepository.save(user);
            return user;
        }


    }
}
