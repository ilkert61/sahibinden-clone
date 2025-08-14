package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Users findByUsername(String username);
}
