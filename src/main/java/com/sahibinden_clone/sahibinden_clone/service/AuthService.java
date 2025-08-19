package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.LoginRequest;
import com.sahibinden_clone.sahibinden_clone.dto.LoginResponse;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsersRepository usersRepository;

    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public LoginResponse login(LoginRequest request) {

        if (request.getUser() == null || request.getUser().isBlank() || request.getPass() == null || request.getPass().isBlank()) {
            return new LoginResponse(false, "Kullanıcı adı/email ve şifre girilmesi zorunludur.", null);
        }
        Optional<Users> userOpt = usersRepository
                .findByEmailIgnoreCaseOrUsernameIgnoreCase(request.getUser(), request.getUser());

        if (userOpt.isEmpty()) {
            return new LoginResponse(false, "Kullanıcı bulunamadı", null);
        }
        //E mail - Username ile aynı columndaki password eşleşme kontrolü
        Users user = userOpt.get();
        if (!request.getPass().equals(user.getPassword())) {
            return new LoginResponse(false, "Şifre hatalı", null);
        } else{
        return new LoginResponse(true, "Giriş başarılı", user.getId());}

    }
}