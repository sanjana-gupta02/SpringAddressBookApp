package com.spring.AddressBook.service;

import com.spring.AddressBook.model.User;
import com.spring.AddressBook.repository.UserRepository;
import com.spring.AddressBook.interfaces.IUserService;
import com.spring.AddressBook.security.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mailSender = mailSender;
    }

    @Override
    public User registerUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password)); // ✅ Hashing password
        return userRepository.save(newUser);
    }

    @Override
    public String loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();

        // ✅ Debugging logs (Check console output)
        System.out.println("Stored Hashed Password: " + user.getPassword());
        System.out.println("Entered Password: " + password);
        System.out.println("Password Match Result: " + passwordEncoder.matches(password, user.getPassword()));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password!");
        }

        return jwtUtil.generateToken(email);
    }

    // ✅ Forgot Password - Generate Reset Token & Send Email
    public String forgotPassword(String email) {
        System.out.println("Received email: " + email);

        Optional<User> userOptional = userRepository.findByEmail(email);

        System.out.println("Database query result: " + userOptional);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();
        String resetToken = jwtUtil.generateToken(email);
        user.setResetToken(resetToken);
        userRepository.save(user);

        sendResetEmail(user.getEmail(), resetToken);

        return "Password reset link has been sent to your email!";
    }


    // ✅ Send Reset Email
    private void sendResetEmail(String email, String token) {
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText("Click the link to reset your password: <a href='" + resetUrl + "'>Reset Password</a>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email. Please try again!");
        }
    }

    public String resetPassword(String token, String newPassword) {
        Optional<User> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid or expired reset token!");
        }

        String email = jwtUtil.extractEmail(token);
        if (email == null || jwtUtil.isTokenExpired(token)) {
            throw new RuntimeException("Reset token has expired! Please request a new one.");
        }

        User user = userOptional.get();

        // Extra Security: Ensure token belongs to the user
        if (!user.getResetToken().equals(token)) {
            throw new RuntimeException("Invalid reset token!");
        }

        user.setPassword(passwordEncoder.encode(newPassword)); // ✅ Hash new password
        user.setResetToken(null); // ✅ Clear reset token after successful reset
        userRepository.save(user);

        // ✅ Send confirmation email after successful password reset
        sendPasswordResetConfirmationEmail(user.getEmail());

        return "Password has been successfully reset!";
    }


    private void sendPasswordResetConfirmationEmail(String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset Successful");
            helper.setText("Your password has been successfully reset. If you did not request this change, please contact support immediately.", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send confirmation email. Please try again!");
        }
    }


}
