package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.LoginRequest;
import com.sahibinden_clone.sahibinden_clone.dto.LoginResponse;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthService {
    private final UsersRepository usersRepository;

    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null
                || request.getUser() == null || request.getUser().isBlank()
                || request.getPass() == null || request.getPass().isBlank()) {
            return new LoginResponse(false, "Kullanıcı adı/email ve şifre gerekli", null, null);
        }

        // Tek satırda hem email hem username ile arıyoruz
        Optional<Users> userOpt = usersRepository
                .findByEmailIgnoreCaseOrUsernameIgnoreCase(request.getUser(), request.getUser());

        if (userOpt.isEmpty()) {
            return new LoginResponse(false, "Kullanıcı bulunamadı", null, null);
        }

        Users user = userOpt.get();

        if (!request.getPass().equals(user.getPassword())) {  // (hash yoksa bu şekilde)
            return new LoginResponse(false, "Şifre hatalı", null, null);
        }

        return new LoginResponse(true, "Giriş başarılı", user.getId(), user.getUsername());
    }
}
