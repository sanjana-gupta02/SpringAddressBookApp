package com.spring.AddressBook.controller;

import com.spring.AddressBook.model.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final List<Contact> contactList = new ArrayList<>();
    private long idCounter = 1L;


    //Get All Contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactList;
    }

    //Get Contact by ID;
    @GetMapping("/{id}")
    public Optional<Contact> getById(@PathVariable long id) {
        return contactList.stream().filter(contact -> contact.getId() == id).findFirst();
    }

    //Post: Add New Contact
    @PostMapping
    public Contact addAllContacts(@RequestBody Contact contact) {
        contact.setId(idCounter++);
        contactList.add(contact);
        return contact;
    }

    //Put: Update Contact by Id
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable long id, @RequestBody Contact updatedContact) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == id) {
                updatedContact.setId(id);
                contactList.set(i, updatedContact);
                return updatedContact;
            }
        }
        return null;
    }

    //Delete by ID
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable long id){
        contactList.removeIf(contact -> contact.getId()==id);
        return "Contact with ID " + id + " deleted.";

    }
}

