package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Gender;
import com.sahibinden_clone.sahibinden_clone.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users user1;
    private Users user2;

    @BeforeEach
    void setUp() {
        user1 = new Users();
        user1.setUsername("ali");
        user1.setFirstname("Ali");
        user1.setLastname("Veli");
        user1.setEmail("ali@mail.com");
        user1.setPhone("5551112233");
        user1.setPassword("123456");
        user1.setGender(Gender.male);
        user1.setBirthdate(LocalDate.of(2000, 1, 1));

        user2 = new Users();
        user2.setUsername("veli");
        user2.setFirstname("Veli");
        user2.setLastname("Kaya");
        user2.setEmail("veli@mail.com");
        user2.setPhone("5552223344");
        user2.setPassword("abcdef");
        user2.setGender(Gender.male);
        user2.setBirthdate(LocalDate.of(1999, 5, 5));

        usersRepository.save(user1);
        usersRepository.save(user2);
    }

    @Test
    void findByUsernameIgnoreCase_shouldReturnUser() {
        Optional<Users> result = usersRepository.findByUsernameIgnoreCase("ALI");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("ali@mail.com");
    }

    @Test
    void existsByUsernameIgnoreCase_shouldReturnTrueIfExists() {
        boolean exists = usersRepository.existsByUsernameIgnoreCase("vELI");

        assertThat(exists).isTrue();
    }

    @Test
    void findByEmailIgnoreCaseOrUsernameIgnoreCase_shouldReturnUserByEmail() {
        Optional<Users> result = usersRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase("veli@mail.com", "x");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("veli");
    }

    @Test
    void existsByEmailIgnoreCaseAndUsernameNot_shouldReturnTrueIfEmailUsedByAnotherUser() {
        boolean exists = usersRepository.existsByEmailIgnoreCaseAndUsernameNot("ali@mail.com", "veli");

        assertThat(exists).isTrue();
    }

    @Test
    void existsByPhoneAndUsernameNot_shouldReturnTrueIfPhoneUsedByAnotherUser() {
        boolean exists = usersRepository.existsByPhoneAndUsernameNot("5551112233", "veli");

        assertThat(exists).isTrue();
    }
}
