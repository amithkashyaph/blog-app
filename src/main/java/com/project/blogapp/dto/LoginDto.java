package com.project.blogapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDto {

    private String usernameOrEmail;
    private String password;
}
