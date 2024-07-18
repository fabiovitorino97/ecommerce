package com.compassUol.ecommerce.services.Impl;

import com.compassUol.ecommerce.dtos.LoginRequestDTO;
import com.compassUol.ecommerce.dtos.LoginResponseDTO;
import com.compassUol.ecommerce.dtos.TokenDTO;
import com.compassUol.ecommerce.dtos.UserDTO;
import com.compassUol.ecommerce.exceptions.InvalidJwtAuthenticationException;
import com.compassUol.ecommerce.models.User;
import com.compassUol.ecommerce.repositories.UserRepository;
import com.compassUol.ecommerce.security.JwtTokenProvider;
import com.compassUol.ecommerce.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<Void> register(UserDTO userDTO) {
        User user = new User();

        user.setUserName(userDTO.username());
        user.setFullName(userDTO.fullname());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setEmail(userDTO.email());
        user.setPermissions(userDTO.permissions());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        emailService.sendMessage(userDTO.email(), "NEW USER", "your user was created successfully");

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        User user = userRepository.findByUsername(data.username());

        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            TokenDTO tokenDTO = tokenProvider.createAccessToken(data.username(), user.getRoles());
            String token = tokenDTO.accessToken();

            return new LoginResponseDTO(user.getUsername(), token);
        } else {
            throw new InvalidJwtAuthenticationException("Invalid authentication");
        }
    }

    public LoginResponseDTO refreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            TokenDTO tokenResponse = tokenProvider.refreshToken(refreshToken);
            String token = tokenResponse.refreshToken();

            return new LoginResponseDTO(user.getUsername(), token);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
