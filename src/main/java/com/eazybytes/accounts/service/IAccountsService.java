package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;

import java.util.List;

public interface IAccountsService {

    /**
     *
     * @param customerDto - CustomerDto object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @return customers
     */
    List<CustomerDto> getAccounts();

    /**
     *
     * @param mobileNumber
     * @return customer
     */
    CustomerDto getAccount(String mobileNumber);

    /**
     *
     * @param customerDto
     * @return boolean.
     */
    Boolean updateAccount(CustomerDto customerDto);
}
