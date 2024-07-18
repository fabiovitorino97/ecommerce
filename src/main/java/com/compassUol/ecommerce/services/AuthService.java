package com.compassUol.ecommerce.services;

import com.compassUol.ecommerce.dtos.LoginRequestDTO;
import com.compassUol.ecommerce.dtos.LoginResponseDTO;
import com.compassUol.ecommerce.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<Void> register(UserDTO userDTO);

    LoginResponseDTO login(LoginRequestDTO data);

    LoginResponseDTO refreshToken(String username, String refreshToken);
}
