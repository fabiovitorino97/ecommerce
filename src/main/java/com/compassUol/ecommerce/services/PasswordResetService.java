package com.compassUol.ecommerce.services;

import com.compassUol.ecommerce.models.PasswordResetToken;
import com.compassUol.ecommerce.models.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PasswordResetService {

    void resetPassword(String token, String newPassword);

    void sendResetToken(String username);
}
