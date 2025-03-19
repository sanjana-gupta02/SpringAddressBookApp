package com.spring.AddressBook.controller;

import com.spring.AddressBook.dto.ContactDTO;
import com.spring.AddressBook.interfaces.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Address Book App", description = "Operations related to managing contacts")
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
    @Operation(summary = "Get all contacts", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of contacts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    // Get Contact by ID
    @Operation(summary = "Get contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved contact", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getById(@PathVariable long id) {
        Optional<ContactDTO> contact = contactService.getById(id);
        return contact.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Add New Contact
    @Operation(summary = "Add a new contact", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added new contact", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.addContact(contactDTO));
    }

    // Update Contact by ID
    @Operation(summary = "Update contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated contact", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable long id, @RequestBody ContactDTO updatedContactDTO) {
        Optional<ContactDTO> updated = contactService.updateContact(id, updatedContactDTO);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Contact by ID
    @Operation(summary = "Delete contact by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted contact"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean removed = contactService.deleteById(id);
        return removed ? ResponseEntity.ok("Contact with ID " + id + " deleted.")
                : ResponseEntity.notFound().build();
    }
}
