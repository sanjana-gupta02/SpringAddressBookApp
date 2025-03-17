package com.spring.AddressBook.service;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.interfaces.IContactService;
import com.spring.AddressBook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service  // Marks this as a Spring Service
public class ContactService implements IContactService {

    List<Contact> contactList = new ArrayList<>();
    private long idCounter = 1L;

    // Convert Contact to ContactDTO
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress());
    }

    // Get all contacts (returns List<ContactDTO>)
    public List<ContactDTO> getAllContacts() {
        List<ContactDTO> contactDTOList = new ArrayList<>();
        for (Contact contact : contactList) {
            contactDTOList.add(convertToDTO(contact));
        }
        return contactDTOList;
    }

    // Get contact by ID (returns Optional<ContactDTO>)
    public Optional<ContactDTO> getById(long id) {
        return contactList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .map(this::convertToDTO);
    }

    // Add a new contact (Accepts ContactDTO and returns ContactDTO)
    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = new Contact(idCounter++, contactDTO.getName(), contactDTO.getPhone(), contactDTO.getEmail(), contactDTO.getAddress());
        contactList.add(contact);
        return convertToDTO(contact);
    }

    // Update contact by ID (Accepts ContactDTO and returns Optional<ContactDTO>)
    public Optional<ContactDTO> updateContact(long id, ContactDTO updatedContactDTO) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == id) {
                Contact updatedContact = new Contact(id, updatedContactDTO.getName(), updatedContactDTO.getPhone(), updatedContactDTO.getEmail(), updatedContactDTO.getAddress());
                contactList.set(i, updatedContact);
                return Optional.of(convertToDTO(updatedContact));
            }
        }
        return Optional.empty();
    }

    // Delete contact by ID
    public boolean deleteById(long id) {
        return contactList.removeIf(contact -> contact.getId() == id);
    }
}
