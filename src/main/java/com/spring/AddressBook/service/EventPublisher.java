package com.spring.AddressBook.service;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.dto.UserDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Publish event for user registration
    public void publishUserRegistrationEvent(UserDTO userDTO) {
        rabbitTemplate.convertAndSend("app.exchange", "user.registration", userDTO);
        System.out.println("User registration event published for: " + userDTO.getEmail());
    }

    // Publish event when a contact is added
    public void publishContactAddedEvent(ContactDTO contactDTO) {
        rabbitTemplate.convertAndSend("app.exchange", "contact.added", contactDTO);
        System.out.println("Contact added event published: " + contactDTO.getName());
    }
}
