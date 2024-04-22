package com.eazybytes.accounts.dto;

import lombok.Data;

// We don't use this annotations in entities because it might cause problems with JPA
@Data
public class CustomerDto {
    private String name;

    private String email;

    private String mobileNumber;

    // We use this approach to combine both Dtos, in case we have large DTO
    // its preferable to create a separate DTO and combine both datss
    private AccountsDto accountsDto;
}
