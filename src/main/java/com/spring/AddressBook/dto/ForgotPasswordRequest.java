package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for the forgot password operation, containing the user's email.")
public class ForgotPasswordRequest {

    @Schema(description = "Email address of the user to send the password reset link", required = true, example = "user@example.com")
    private String email;
}
