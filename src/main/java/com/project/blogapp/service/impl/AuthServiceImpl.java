package com.project.blogapp.service.impl;

import com.project.blogapp.dto.LoginDto;
import com.project.blogapp.dto.RegisterUserDTO;
import com.project.blogapp.exception.BlogAPIException;
import com.project.blogapp.model.Role;
import com.project.blogapp.model.User;
import com.project.blogapp.repository.RoleRepository;
import com.project.blogapp.repository.UserRepository;
import com.project.blogapp.security.JwtTokenProvider;
import com.project.blogapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.generateJWTToken(authentication);


        return jwtToken;
    }

    @Override
    public String register(RegisterUserDTO registerUserDTO) {

        // check if username already exists
        if(userRepository.existsByUsername(registerUserDTO.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check if email already exists
        if(userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roleSet.add(role);

        User user = User.builder()
                .name(registerUserDTO.getName())
                .email(registerUserDTO.getEmail())
                .username(registerUserDTO.getUsername())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .roles(roleSet)
                .build();


        userRepository.save(user);

        return "User registered successfully!";
    }
}
