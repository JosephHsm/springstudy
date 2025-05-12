package com.example.demo1.controller.auth;

import com.example.demo1.controller.auth.dto.*;
import com.example.demo1.datalogic.userpack.User;
import com.example.demo1.datalogic.userpack.UserRepository;
import com.example.demo1.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Validated AuthRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User u = new User();
        u.setEmail(req.getEmail());
        u.setUsername(req.getNickname());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepo.save(u);

        String token = tokenProvider.createToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(u.getEmail(), u.getUsername(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Validated AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        User u = userRepo.findByEmail(req.getEmail()).get();
        String token = tokenProvider.createToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(u.getEmail(), u.getUsername(), token));
    }
}
