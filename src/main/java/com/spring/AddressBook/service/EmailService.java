package com.spring.AddressBook.service;

import com.spring.AddressBook.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public static void sendWelcomeEmail(UserDTO userDTO) {
        // Implement actual email sending logic here, for example, using JavaMailSender
        // For now, it's a simple print statement simulating the email send.
        System.out.println("Sending welcome email to: " + userDTO.getEmail());
        // You can use an SMTP server or email service like SendGrid, Amazon SES, etc., to send real emails
    }
}
