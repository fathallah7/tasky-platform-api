package com.crud.demo.controller;

import com.crud.demo.dto.ApiResponse;
import com.crud.demo.models.User;
import com.crud.demo.repository.UserRepository;
import com.crud.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ApiResponse.error("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return ApiResponse.success("User registered successfully!", savedUser);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ApiResponse.success("Login successful", token);
        } else {
            return ApiResponse.error("Invalid email or password!");
        }
    }
}
