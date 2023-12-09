package com.project.blogapp.controller;

import com.project.blogapp.dto.RegisterUserDTO;
import com.project.blogapp.model.Role;
import com.project.blogapp.model.User;
import com.project.blogapp.repository.RoleRepository;
import com.project.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/create")
    public User createUser(@RequestBody RegisterUserDTO registerUserDTO) {
        Role role = roleRepository.findByName("ROLE_USER").get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = User.builder().email(registerUserDTO.getEmail())
                .name(registerUserDTO.getName())
                .username(registerUserDTO.getUsername())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .roles(roleSet)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @PostMapping("/roles/create")
    public Role createRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }
}
