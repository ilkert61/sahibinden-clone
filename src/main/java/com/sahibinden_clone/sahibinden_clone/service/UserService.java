package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.EditUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users addUser(UserDTO userDTO) throws Exception {
        if (usersRepository.existsByUsernameIgnoreCase(userDTO.getUsername())) {
            throw new Exception("Kullanıcı adı zaten alınmış.");
        }

        if (userDTO.getEmail() != null &&
                usersRepository.existsByEmailIgnoreCaseAndUsernameNot(
                        userDTO.getEmail().trim().toLowerCase(),
                        userDTO.getUsername()
                )) {
            throw new Exception("Bu e-posta zaten kullanımda.");
        }

        if (userDTO.getPhone() != null &&
                usersRepository.existsByPhoneAndUsernameNot(
                        userDTO.getPhone().trim(),
                        userDTO.getUsername()
                )) {
            throw new Exception("Bu telefon numarası zaten kullanımda.");
        }

        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setBirthdate(userDTO.getBirthday());

        return usersRepository.save(user);
    }

    public void deleteUserWithPasswordByUsername(String username, String password) throws Exception {
        Users user = usersRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new Exception("Kullanıcı bulunamadı."));
        if (!user.getPassword().equals(password)) {
            throw new Exception("Girilen şifre yanlış.");
        }
        usersRepository.delete(user);
    }

    @Transactional
    public Users updateUserContactByUsername(String username, EditUserRequest request) {
        Users user = usersRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı."));

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            String newEmail = request.getEmail().trim().toLowerCase();
            if (usersRepository.existsByEmailIgnoreCaseAndUsernameNot(newEmail, username)) {
                throw new IllegalArgumentException("Bu e-posta zaten kullanımda.");
            }
            user.setEmail(newEmail);
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            String newPhone = request.getPhone().replaceAll("\\s+", "").trim();
            if (usersRepository.existsByPhoneAndUsernameNot(newPhone, username)) {
                throw new IllegalArgumentException("Bu telefon numarası zaten kullanımda.");
            }
            user.setPhone(newPhone);
        }

        return usersRepository.save(user);
    }
}
