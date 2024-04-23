package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// We don't use this annotations in entities because it might cause problems with JPA
@Data
public class CustomerDto {
    // We get the annotations below from *spring-boot-starter-validation*

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, max = 30, message = "Check the length")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Value not valid !!")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^\\d{8}$", message = "Must be 8 digits")
    /*Explanation of the regex pattern:
    – ^ asserts the start of the string.
    – \d matches any digit (0-9).
    – {10} specifies that the previous digit pattern should occur exactly 10 times.
    – $ asserts the end of the string.
*/
    private String mobileNumber;

    // We use this approach to combine both Dtos, in case we have large DTO
    // it's preferable to create a separate DTO and combine both datss
    private AccountsDto accountsDto;
}
