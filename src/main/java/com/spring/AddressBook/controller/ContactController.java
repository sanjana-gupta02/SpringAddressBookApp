package com.spring.AddressBook.controller;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final IContactService contactService;

    // Constructor-based Dependency Injection
    @Autowired
    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    // Get All Contacts
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    // Get Contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getById(@PathVariable long id) {
        Optional<ContactDTO> contact = contactService.getById(id);
        return contact.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Post: Add New Contact
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.addContact(contactDTO));
    }

    // Put: Update Contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable long id, @RequestBody ContactDTO updatedContactDTO) {
        Optional<ContactDTO> updated = contactService.updateContact(id, updatedContactDTO);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean removed = contactService.deleteById(id);
        return removed ? ResponseEntity.ok("Contact with ID " + id + " deleted.")
                : ResponseEntity.notFound().build();
    }
}
