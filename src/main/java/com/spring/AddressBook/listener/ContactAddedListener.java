package com.spring.AddressBook.listener;
import com.spring.AddressBook.dto.ContactDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.spring.AddressBook.service.NotificationService;

@Component
public class ContactAddedListener {

    @RabbitListener(queues = "contact.added.queue")
    public void handleContactAddedEvent(ContactDTO contactDTO) {
        // Perform any necessary action when a contact is added
        // For example, you can log it or send notifications
        NotificationService.notifyContactAdded(contactDTO);
    }
}
