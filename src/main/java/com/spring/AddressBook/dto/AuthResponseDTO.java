package com.spring.AddressBook.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponseDTO {
    private String message;
    private String token;
}
