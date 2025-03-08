package com.spring.AddressBook.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
