package com.geekbrains.geekspring.services;

import com.geekbrains.geekspring.entities.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);
    Role findById(Long id);
    List<Role> findAll();
}
