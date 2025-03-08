package com.spring.AddressBook.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactDTO {
    private long id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
