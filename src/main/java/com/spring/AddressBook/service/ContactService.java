package com.spring.AddressBook.service;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.interfaces.IContactService;
import com.spring.AddressBook.model.Contact;
import com.spring.AddressBook.repository.ContactRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Cache all contacts in Redis
    @Cacheable(value = "contacts", key = "#root.method.name")
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress()))
                .collect(Collectors.toList());
    }

    // Cache individual contact by ID
    @Cacheable(value = "contacts", key = "#id")
    public Optional<ContactDTO> getById(long id) {
        return contactRepository.findById(id)
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress()));
    }

    // Add new contact and update cache (when adding, it will store the contact in Redis)
    @CachePut(value = "contacts", key = "#contactDTO.id")
    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = new Contact(contactDTO.getName(), contactDTO.getPhone(), contactDTO.getEmail(), contactDTO.getAddress());
        contact = contactRepository.save(contact);
        return new ContactDTO(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress());
    }

    // Update an existing contact and update cache
    @Override
    @CachePut(value = "contacts", key = "#id")
    public Optional<ContactDTO> updateContact(long id, ContactDTO updatedContactDTO) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            // Update the contact's fields
            contact.setName(updatedContactDTO.getName());
            contact.setPhone(updatedContactDTO.getPhone());
            contact.setEmail(updatedContactDTO.getEmail());
            contact.setAddress(updatedContactDTO.getAddress());

            contact = contactRepository.save(contact); // Save the updated contact to the database

            // Return the updated contact as a DTO
            return Optional.of(new ContactDTO(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress()));
        }
        return Optional.empty();
    }

    // Remove contact from cache when deleting (it will remove the cached contact from Redis)
    @CacheEvict(value = "contacts", key = "#id")
    public boolean deleteById(long id) {
        contactRepository.deleteById(id);
        return true;
    }
}
