package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.LoginRequest;
import com.sahibinden_clone.sahibinden_clone.dto.LoginResponse;
import com.sahibinden_clone.sahibinden_clone.entity.Gender;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import com.sahibinden_clone.sahibinden_clone.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private AuthService authService;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@mail.com");
        user.setPassword("123456");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPhone("5551112233");
        user.setGender(Gender.MALE);
        user.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    void login_shouldFail_whenUserOrPassBlank() {
        LoginResponse res1 = authService.login(new LoginRequest("", "123456"));
        assertThat(res1.isSuccess()).isFalse();
        assertThat(res1.getMessage()).contains("zorunludur");

        LoginResponse res2 = authService.login(new LoginRequest("testuser", " "));
        assertThat(res2.isSuccess()).isFalse();
        assertThat(res2.getMessage()).contains("zorunludur");
    }

    @Test
    void login_shouldFail_whenUserNotFound() {
        when(usersRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("nouser", "nouser"))
                .thenReturn(Optional.empty());

        LoginResponse res = authService.login(new LoginRequest("nouser", "123456"));

        assertThat(res.isSuccess()).isFalse();
        assertThat(res.getMessage()).contains("Kullanıcı bulunamadı");
        assertThat(res.getUserId()).isNull();
    }

    @Test
    void login_shouldFail_whenPasswordWrong() {
        when(usersRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("testuser", "testuser"))
                .thenReturn(Optional.of(user));

        LoginResponse res = authService.login(new LoginRequest("testuser", "wrongpass"));

        assertThat(res.isSuccess()).isFalse();
        assertThat(res.getMessage()).contains("Şifre hatalı");
        assertThat(res.getUserId()).isNull();
    }

    @Test
    void login_shouldSucceed_whenCredentialsMatch_byUsername() {
        when(usersRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("testuser", "testuser"))
                .thenReturn(Optional.of(user));

        LoginResponse res = authService.login(new LoginRequest("testuser", "123456"));

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getMessage()).contains("Giriş başarılı");
        assertThat(res.getUserId()).isEqualTo(user.getId());
    }

    @Test
    void login_shouldSucceed_whenCredentialsMatch_byEmail() {
        when(usersRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("test@mail.com", "test@mail.com"))
                .thenReturn(Optional.of(user));

        LoginResponse res = authService.login(new LoginRequest("test@mail.com", "123456"));

        assertThat(res.isSuccess()).isTrue();
        assertThat(res.getUserId()).isEqualTo(user.getId());
    }
}
