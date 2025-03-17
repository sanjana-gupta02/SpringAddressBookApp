package com.spring.AddressBook.controller;


import com.spring.AddressBook.dto.RegisterRequest;
import com.spring.AddressBook.model.User;
import com.spring.AddressBook.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        User newUser = userService.registerUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User registered successfully with email: " + newUser.getEmail());
    }
}



