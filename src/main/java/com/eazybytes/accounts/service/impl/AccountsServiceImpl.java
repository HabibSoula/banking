package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourcesNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    // No need for @Autowired because there's only a single constructor
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;


    /**
     * @param customerDto - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        // If an account is created with the same phone number, an exception will be thrown
        //
        if (optionalCustomer.isPresent())
        {
         throw new CustomerAlreadyExistsException("Customer already registered with this phone number  "+ customerDto.getMobileNumber());
        }
        customer.setCreatedBy("habib");
        customer.setCreatedAt(LocalDateTime.now());
    Customer savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));

    }

    /**
     *
     * @return customerDtoList
     */

    @Override
    public List<CustomerDto> getAccounts() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = CustomerMapper.mapToCustomerDtoList(customerList, new ArrayList<>());
        for (int i = 0; i <customerList.size(); i++)
        {
            Accounts accounts = accountRepository.findByCustomerId(customerList.get(i).getCustomerId()).orElseThrow(
                    () -> new ResourcesNotFoundException("Account", "CustomerId", customerList.get(1).getCustomerId().toString())
            );
            customerDtoList.get(i).setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
            log.info(
                    "this is the branch adress: "+ customerDtoList.get(i).getAccountsDto().getBranchAddress()
            );
        }
        return customerDtoList;
    }

    /**
     *
     * @param mobileNumber string
     * @return the details of the customer
     */

    @Override
    public CustomerDto getAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourcesNotFoundException("Customer","MobileNumber",mobileNumber)
        );

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourcesNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto
     * @return boolean, if the update is successful or not
     */

    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourcesNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourcesNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    public void deleteAccount(String phoneNumber)
    {
        Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourcesNotFoundException("Customer","MobileNumber",phoneNumber)
        );

        customerRepository.delete(customer);

    }


    /**
     *
     * @param customer
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer)
    {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long accountNumber = 10000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress("TUNIS");
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Habib");
        return newAccount;
    }
}
