package com.geekbrains.geekspring.repositories;

import com.geekbrains.geekspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUserName(String userName);
}
