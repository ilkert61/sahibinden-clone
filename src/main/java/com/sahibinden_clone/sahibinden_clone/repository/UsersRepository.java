package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Users;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findByUsername(String username);
    Optional<Users> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
}
