package com.spring.AddressBook.controller;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final List<Contact> contactList = new ArrayList<>();
    private long idCounter = 1L;

    // Convert Contact to DTO
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getId(),contact.getName(), contact.getPhone(), contact.getEmail(), contact.getAddress());
    }


    //Get All Contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactList);
    }

    //Get Contact by ID;
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getById(@PathVariable long id) {
        Optional<Contact> contact = contactList.stream().filter(c -> c.getId() == id).findFirst();
        return contact.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //Post: Add New Contact
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact(idCounter++, contactDTO.getName(), contactDTO.getPhone(), contactDTO.getEmail(), contactDTO.getAddress());
        contactList.add(contact);
        return ResponseEntity.ok(convertToDTO(contact));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable long id, @RequestBody ContactDTO updatedContactDTO) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == id) {
                Contact updatedContact = new Contact(id, updatedContactDTO.getName(), updatedContactDTO.getPhone(), updatedContactDTO.getEmail(), updatedContactDTO.getAddress());
                contactList.set(i, updatedContact);
                return ResponseEntity.ok(convertToDTO(updatedContact));
            }
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean removed = contactList.removeIf(contact -> contact.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Contact with ID " + id + " deleted.");
        }
        return ResponseEntity.notFound().build();
    }

}

