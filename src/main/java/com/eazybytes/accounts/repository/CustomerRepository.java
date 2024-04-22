package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // We use optional because the phone number might or might not have a mobile number
    // this is an *abstract* class
    // SpringJPA will understand that we are trying to execute a select query by the keyword "findBy",
    // the column name must match the calling of the class, not CASE SENSITIVE
    // you can use and if you want to have multiple columns (findByMobileNumberAnd)

    /**
     *
     * Derived Name method
     *
     */
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
