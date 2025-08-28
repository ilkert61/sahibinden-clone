package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsernameIgnoreCase(String username);
    Optional<Users> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCaseAndUsernameNot(String email, String username);
    boolean existsByPhoneAndUsernameNot(String phone, String username);
}
