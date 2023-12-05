package com.project.blogapp.controller;

import com.project.blogapp.dto.JwtAuthResponse;
import com.project.blogapp.dto.LoginDto;
import com.project.blogapp.dto.RegisterUserDTO;
import com.project.blogapp.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "CRUD APIs for Auth flow"
)
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = { "/login", "/signin" })
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String jwtToken = authService.login(loginDto);

        JwtAuthResponse response = JwtAuthResponse.builder()
                .accessToken(jwtToken)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        return ResponseEntity.ok(authService.register(registerUserDTO));
    }
}
