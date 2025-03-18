package com.spring.AddressBook.listener;

import com.spring.AddressBook.service.EmailService;
import com.spring.AddressBook.dto.UserDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    @RabbitListener(queues = "user.registration.queue")
    public void handleUserRegistrationEvent(UserDTO userDTO) {
        // Send a welcome email to the user using the data from userDTO
        EmailService.sendWelcomeEmail(userDTO);
    }
}



