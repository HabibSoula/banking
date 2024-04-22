package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;
    @PostMapping("/create")
    // We use ResponseEntity to be able to return https related stuff to the log
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> getAccountDetails (@RequestParam String mobileNumber)
    {
       CustomerDto customerDto = iAccountsService.getAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber)
    {
        iAccountsService.deleteAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDto(
                AccountConstants.STATUS_201, AccountConstants.MESSAGE_417_DELETE

        ));
    }
}
