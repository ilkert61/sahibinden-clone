package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.EditUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    public Users addUser(@RequestBody UserDTO userDTO) throws Exception {
        if (usersRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new Exception("Kullanıcı adı zaten alınmış.");
        }
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword()); // TODO: BCrypt ile hashle
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setBirthdate(userDTO.getBirthday());
        Users saved = usersRepository.save(user);
        return saved;
    }

    public void deleteUserWithPassword(UUID userId, String password) throws Exception {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new Exception("Kullanıcı bulunamadı."));

        if (!user.getPassword().equals(password)) { // TODO: encoder.matches(...)
            throw new Exception("Girilen şifre yanlış.");
        }
        usersRepository.delete(user);
    }

    @Transactional
    public Users updateUserContact(UUID userId, EditUserRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));

        if (usersRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), userId)) {
            throw new IllegalArgumentException("Bu e-posta zaten kullanımda.");
        }

        String normalizedPhone = request.getPhone().replaceAll("\\s+", "").trim();
        user.setEmail(request.getEmail().trim());
        user.setPhone(normalizedPhone);

        Users updated = usersRepository.save(user);
        return updated;
    }
}
