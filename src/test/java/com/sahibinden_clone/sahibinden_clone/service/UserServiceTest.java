package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.EditUserRequest;
import com.sahibinden_clone.sahibinden_clone.dto.UserDTO;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private Users user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("Ali","Veli","ali@mail.com","5551112233",
                "aliveli","123456", Gender.male, LocalDate.of(2000,1,1));

        user = new Users();
        user.setUsername("aliveli");
        user.setFirstname("Ali");
        user.setLastname("Veli");
        user.setEmail("ali@mail.com");
        user.setPhone("5551112233");
        user.setPassword("123456");
        user.setGender(Gender.male);
        user.setBirthdate(LocalDate.of(2000,1,1));
    }

    @Test
    void addUser_shouldSave_whenUnique() throws Exception {
        when(usersRepository.existsByUsernameIgnoreCase("aliveli")).thenReturn(false);
        when(usersRepository.existsByEmailIgnoreCaseAndUsernameNot("ali@mail.com", "aliveli")).thenReturn(false);
        when(usersRepository.existsByPhoneAndUsernameNot("5551112233", "aliveli")).thenReturn(false);
        when(usersRepository.save(any())).thenReturn(user);

        Users saved = userService.addUser(userDTO);

        assertThat(saved.getUsername()).isEqualTo("aliveli");
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    void addUser_shouldThrow_whenUsernameExists() {
        when(usersRepository.existsByUsernameIgnoreCase("aliveli")).thenReturn(true);

        assertThatThrownBy(() -> userService.addUser(userDTO))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Kullanıcı adı zaten alınmış");
    }

    @Test
    void deleteUserWithPassword_shouldDelete_whenPasswordMatches() throws Exception {
        when(usersRepository.findByUsernameIgnoreCase("aliveli")).thenReturn(Optional.of(user));

        userService.deleteUserWithPasswordByUsername("aliveli", "123456");

        verify(usersRepository).delete(user);
    }

    @Test
    void deleteUserWithPassword_shouldThrow_whenPasswordWrong() {
        when(usersRepository.findByUsernameIgnoreCase("aliveli")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.deleteUserWithPasswordByUsername("aliveli", "wrong"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Girilen şifre yanlış");
    }

    @Test
    void updateUserContact_shouldUpdateEmail() {
        EditUserRequest req = new EditUserRequest();
        req.setEmail("new@mail.com");

        when(usersRepository.findByUsernameIgnoreCase("aliveli")).thenReturn(Optional.of(user));
        when(usersRepository.existsByEmailIgnoreCaseAndUsernameNot("new@mail.com","aliveli")).thenReturn(false);
        when(usersRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Users updated = userService.updateUserContactByUsername("aliveli", req);

        assertThat(updated.getEmail()).isEqualTo("new@mail.com");
    }
}
