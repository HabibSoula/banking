package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// We use this annotation to tell spring that this is a generic exception handler, for all controller
// We extend this class, to be able to return the exceptions whenever an input is not valid.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // We create an empty map for errors
        Map<String, String> validationErrors = new HashMap<>();
        // Get all errors
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();


        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    /*Spring boot will look if there are user specified exceptions like CustomerAlreadyExistsException, if not, it'll run this global exception
          This annotation indicates that the method is capable of handling any exception (Exception.class) that occurs within the controller or its mappings.*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException  (Exception exception, WebRequest wr)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                //when we put false, it only returns the ApiPath
                wr.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()

        );
        /* By returning a ResponseEntity object, the method provides flexibility in customizing the response
        sent back to the client. It allows you to set not only the HTTP status code but also any additional
        headers or other details you might need in the response.

        In this specific case, the method is returning a response with the error details encapsulated in an
        ErrorResponseDto object and a status code of 400 (Bad Request), indicating that the client's request was not
        processed successfully due to a client error. */
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

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
