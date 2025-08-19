package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.dto.DeleteUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.EditUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @Operation(summary = "Yeni kullanıcı ekle")
    @PostMapping("/addUsers")
    public ResponseEntity<Users> addUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @Operation(summary = "Kullanıcıyı username + şifre ile sil")
    @DeleteMapping("/username/{username}")
    public ResponseEntity<String> deleteUserByUsername(
            @Parameter(in = ParameterIn.PATH, name = "username", required = true, description = "Kullanıcı adı")
            @PathVariable("username") String username,
            @RequestBody @Valid DeleteUserRequest request) {
        try {
            userService.deleteUserWithPasswordByUsername(username, request.getPassword());
            return ResponseEntity.ok("Kullanıcı silindi.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Kullanıcının email/telefon bilgisini (kısmi) güncelle")
    @PutMapping("/username/{username}/contact")
    public ResponseEntity<Users> updateContactByUsername(
            @Parameter(in = ParameterIn.PATH, name = "username", required = true, description = "Kullanıcı adı")
            @PathVariable("username") String username,
            @Valid @RequestBody EditUserRequest request) {
        return ResponseEntity.ok(userService.updateUserContactByUsername(username, request));
    }
}
