package com.spring.AddressBook.interfaces;

import com.spring.AddressBook.dto.ContactDTO;

import java.util.List;
import java.util.Optional;

public interface IContactService {

    List<ContactDTO> getAllContacts();

    Optional<ContactDTO> getById(long id);

    ContactDTO addContact(ContactDTO contactDTO);

    Optional<ContactDTO> updateContact(long id, ContactDTO updatedContactDTO);

    boolean deleteById(long id);
}
