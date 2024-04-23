package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated // We use this @, to tell Spring boot to apply the validators in this controller
public class AccountsController {

    // If we don't use @AllArgsConstructor, or @Autowired, this will be NULL.
    @Autowired
    private IAccountsService iAccountsService;

    // For the methods requiring the CustomerDto (which includes validators), we use the @Valid annotation, for the ones requiring the mobile number, we add manually the validator.

    @PostMapping("/create")
    // We use ResponseEntity to be able to return https related stuff to the log
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountConstants.STATUS_201, AccountConstants.MESSAGE_201

                ));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> fetchAccounts (){
       List <CustomerDto> customerDto = iAccountsService.getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @GetMapping("/getDetails")
    public ResponseEntity<CustomerDto> getAccountDetails (@RequestParam
                                                              @Pattern(regexp = "^\\d{8}$", message = "Must be 8 digits")
                                                              String mobileNumber)
    {
       CustomerDto customerDto = iAccountsService.getAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^\\d{8}$", message = "Must be 8 digits")
                                                         String mobileNumber)
    {
        iAccountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDto(
                AccountConstants.STATUS_201, AccountConstants.MESSAGE_417_DELETE

        ));
    }
}
