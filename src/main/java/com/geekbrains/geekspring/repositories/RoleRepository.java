package com.geekbrains.geekspring.repositories;

import com.geekbrains.geekspring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findOneByName(String theRoleName);
}
