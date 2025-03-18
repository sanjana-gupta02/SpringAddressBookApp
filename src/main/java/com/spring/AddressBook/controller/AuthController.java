package com.spring.AddressBook.controller;

import com.spring.AddressBook.dto.LoginDTO;
import com.spring.AddressBook.dto.RegisterRequest;
import com.spring.AddressBook.dto.AuthResponseDTO;
import com.spring.AddressBook.model.User;
import com.spring.AddressBook.service.UserService;
import com.spring.AddressBook.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Register User & Return JWT Token
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.registerUser(request.getEmail(), request.getPassword());
            String token = jwtUtil.generateToken(newUser.getEmail());

            return ResponseEntity.ok(new AuthResponseDTO("User registered successfully", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(e.getMessage(), null));
        }
    }

    // ✅ Login User & Return JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok(new AuthResponseDTO("Login successful", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO(e.getMessage(), null));
        }
    }
}
