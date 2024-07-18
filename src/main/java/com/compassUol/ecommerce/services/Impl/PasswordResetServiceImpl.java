package com.compassUol.ecommerce.services.Impl;

import com.compassUol.ecommerce.exceptions.PasswordResetTokenNotFoundException;
import com.compassUol.ecommerce.models.PasswordResetToken;
import com.compassUol.ecommerce.models.User;
import com.compassUol.ecommerce.repositories.PasswordResetTokenRepository;
import com.compassUol.ecommerce.repositories.UserRepository;
import com.compassUol.ecommerce.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new PasswordResetTokenNotFoundException("Invalid token"));

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.deleteById(resetToken.getId());
    }

    public void sendResetToken(String username) {
        User user = userRepository.findByUsername(username);

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);

        emailService.sendMessage(user.getEmail(), "Password Reset Request",
                "To reset your password, click the link below:\n" +
                        "http://localhost:8080/api/reset-password/reset?token=" + token);
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(calculateExpiryDate(24 * 60));
        passwordResetTokenRepository.save(myToken);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
