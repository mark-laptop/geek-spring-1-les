package com.geekbrains.geekspring.repositories;

import com.geekbrains.geekspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUserName(String userName);
}
