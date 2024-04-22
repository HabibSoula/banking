package com.eazybytes.accounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends RuntimeException {
   public CustomerAlreadyExistsException(String message){
       /*In Java, when you extend a class (in this case, RuntimeException),
        the subclass constructor implicitly calls the constructor of its superclass (the class it extends).
        However, if the superclass constructor requires parameters,
        you need to explicitly call it using super() in the subclass constructor. */
    super(message);
   }
}
