package com.spring.AddressBook.service;

import com.spring.AddressBook.dto.ContactDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public static void notifyContactAdded(ContactDTO contactDTO) {
        // Implement actual notification logic here, such as sending an email or push notification
        System.out.println("New contact added: " + contactDTO.getName());
        // Here you can integrate with your notification service (e.g., Twilio for SMS, Firebase for push notifications, etc.)
    }
}
