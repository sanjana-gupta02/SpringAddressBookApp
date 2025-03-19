package com.spring.AddressBook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Details about the contact")
public class ContactDTO {

    @Schema(description = "The unique ID of the contact", required = true, example = "1")
    private long id;

    @Schema(description = "The name of the contact", required = true, example = "John Doe")
    private String name;

    @Schema(description = "The phone number of the contact", required = true, example = "+1234567890")
    private String phone;

    @Schema(description = "The email address of the contact", example = "contact@example.com")
    private String email;

    @Schema(description = "The address of the contact", example = "123 Main St, City, Country")
    private String address;
}
