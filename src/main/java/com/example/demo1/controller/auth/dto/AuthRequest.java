package com.example.demo1.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // signup 시에만 사용
    private String nickname;
}
