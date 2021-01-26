package com.geekbrains.geekspring.services;

import com.geekbrains.geekspring.entities.Role;
import com.geekbrains.geekspring.exception.RoleNotFoundException;
import com.geekbrains.geekspring.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findOneByName(name);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(
                String.format("Not found role by id: %s", id)
        ));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
