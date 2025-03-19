package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Request model for user login containing email and password.")
public class LoginDTO {

    @Schema(description = "Email address of the user", required = true, example = "user@example.com")
    private String email;

    @Schema(description = "Password of the user", required = true, example = "password123")
    private String password;
}
