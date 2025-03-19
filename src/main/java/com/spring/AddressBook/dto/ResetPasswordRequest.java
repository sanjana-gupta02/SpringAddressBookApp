package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for resetting the user's password using a token and new password.")
public class ResetPasswordRequest {

    @Schema(description = "Password reset token", required = true, example = "d16f1e2b3c4d5e6f7g8h9i10jklmnopr")
    private String token; // This is the token for verifying the reset request.

    @Schema(description = "The new password for the user", required = true, example = "newpassword123")
    private String newPassword; // This is the new password the user wants to set.
}
