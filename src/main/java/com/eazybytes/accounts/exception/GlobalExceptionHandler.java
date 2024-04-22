package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

//We use this annotation to tell spring that this is a generic exception handler, for all controller
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsCustomer (CustomerAlreadyExistsException exception, WebRequest wr)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                //when we put false, it only returns the ApiPath
                wr.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()

        );
        /* By returning a ResponseEntity object, the method provides flexibility in customizing the response
        sent back to the client. It allows you to set not only the HTTP status code but also any additional
        headers or other details you might need in the response.

        In this specific case, the method is returning a response with the error details encapsulated in an
        ErrorResponseDto object and a status code of 400 (Bad Request), indicating that the client's request was not
        processed successfully due to a client error. */
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourcesNotFound (ResourcesNotFoundException exception, WebRequest wr)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                //when we put false, it only returns the ApiPath
                wr.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()

        );
        /* By returning a ResponseEntity object, the method provides flexibility in customizing the response
        sent back to the client. It allows you to set not only the HTTP status code but also any additional
        headers or other details you might need in the response.

        In this specific case, the method is returning a response with the error details encapsulated in an
        ErrorResponseDto object and a status code of 400 (Bad Request), indicating that the client's request was not
        processed successfully due to a client error. */
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND) ;
    }
}
