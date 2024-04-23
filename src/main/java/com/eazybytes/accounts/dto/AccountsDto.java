package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// We don't use this annotations in entities because it might cause problems with JPA
@Data
public class AccountsDto {
    // We get the annotations below from *spring-boot-starter-validation*

    @NotEmpty(message = "Name cannot be empty")
    private Long accountNumber;

    @NotEmpty(message = "Type cannot be empty")
    private String accountType;

    @NotEmpty(message = "Address cannot be empty")
    private String branchAddress;
}
