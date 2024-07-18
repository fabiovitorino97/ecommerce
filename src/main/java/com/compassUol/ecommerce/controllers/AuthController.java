package com.compassUol.ecommerce.controllers;

import com.compassUol.ecommerce.dtos.LoginRequestDTO;
import com.compassUol.ecommerce.dtos.LoginResponseDTO;
import com.compassUol.ecommerce.dtos.UserDTO;
import com.compassUol.ecommerce.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO data) {
        return authService.register(data);
    }

    @PostMapping(value = "/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO data) {
        return authService.login(data);
    }

    /*@PutMapping(value = "/refresh/{username}")
    public LoginResponseDTO refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(username, refreshToken);
    }*/
}
