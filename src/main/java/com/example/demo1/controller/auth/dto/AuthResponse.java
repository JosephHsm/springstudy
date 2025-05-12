package com.example.demo1.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private final String email;
    private final String nickname;
    private final String accessToken;
}
