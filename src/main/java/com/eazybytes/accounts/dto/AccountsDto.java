package com.eazybytes.accounts.dto;

import lombok.Data;

// We don't use this annotations in entities because it might cause problems with JPA
@Data
public class AccountsDto {
    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
