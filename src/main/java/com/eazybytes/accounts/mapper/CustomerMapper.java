package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    // A mapper for a list.
    public static List<CustomerDto> mapToCustomerDtoList(List<Customer> customerList, List<CustomerDto> customerDtoList) {
        customerDtoList = new ArrayList<CustomerDto>( customerList.size() );
        for ( Customer customer : customerList ) {
            customerDtoList.add( mapToCustomerDto(customer, new CustomerDto()) );
        }

        return customerDtoList;
    }

}
