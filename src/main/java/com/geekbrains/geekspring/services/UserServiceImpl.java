package com.geekbrains.geekspring.services;

import com.geekbrains.geekspring.entities.Role;
import com.geekbrains.geekspring.entities.SystemUser;
import com.geekbrains.geekspring.entities.User;
import com.geekbrains.geekspring.exception.UserNotFoundException;
import com.geekbrains.geekspring.repositories.RoleRepository;
import com.geekbrains.geekspring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		return userRepository.findOneByUserName(userName);
	}

	@Override
	@Transactional
	public void save(SystemUser systemUser) {
		User user = toUser(systemUser);
		userRepository.save(user);
	}

	@Override
	public List<SystemUser> getAll() {
		return userRepository.findAll().stream().map(this::toSystemUser).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public SystemUser findById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
				String.format("User not found by id: %s", id)
		));
		return toSystemUser(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findOneByUserName(userName).orElseThrow(() ->
				new UsernameNotFoundException("Invalid username or password."));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
	}

	private SystemUser toSystemUser(User user) {
		return SystemUser.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.roles(user.getRoles())
				.build();
	}

	private User toUser(SystemUser systemUser) {
		return User.builder()
				.id(systemUser.getId())
				.userName(systemUser.getUserName())
				.firstName(systemUser.getFirstName())
				.lastName(systemUser.getLastName())
				.email(systemUser.getEmail())
				.roles(systemUser.getRoles())
				.build();
	}
}
