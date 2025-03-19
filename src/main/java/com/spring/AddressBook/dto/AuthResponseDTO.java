package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for authentication operations like login and registration.")
public class AuthResponseDTO {

    @Schema(description = "Message describing the status of the authentication operation", example = "User registered successfully")
    private String message;

    @Schema(description = "JWT token issued after successful authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
