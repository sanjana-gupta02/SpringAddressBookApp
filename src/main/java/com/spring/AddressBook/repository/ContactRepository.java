package com.spring.AddressBook.repository;

import com.spring.AddressBook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
