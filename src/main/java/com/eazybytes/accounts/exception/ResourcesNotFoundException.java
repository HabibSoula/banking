package com.eazybytes.accounts.exception;

public class ResourcesNotFoundException extends RuntimeException{

    public ResourcesNotFoundException(String resources, String field, String value)
    {
        super(String.format("%s is not found with given input data %s: %s", resources,field,value));
    }
}
