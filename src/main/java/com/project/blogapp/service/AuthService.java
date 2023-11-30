package com.project.blogapp.service;

import com.project.blogapp.dto.LoginDto;
import com.project.blogapp.dto.RegisterUserDTO;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterUserDTO registerUserDTO);
}
