package com.geekbrains.geekspring.repositories;

import com.geekbrains.geekspring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findOneByName(String theRoleName);
}
