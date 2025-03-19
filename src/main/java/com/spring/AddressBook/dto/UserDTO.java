package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Details about the user for registration or login")
public class UserDTO {

    @Schema(description = "The email address of the user", required = true, example = "user@example.com")
    private String email;

    @Schema(description = "The password of the user", required = true, example = "password123")
    private String password;
}
