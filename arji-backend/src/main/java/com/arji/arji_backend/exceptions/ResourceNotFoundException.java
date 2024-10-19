package com.arji.arji_backend.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String propertyName, Long resourceId) {
        super(String.format("Cannot find %s with %s:%s", resourceName, propertyName, resourceId));
    }

    public ResourceNotFoundException(String resourceName, String propertyName, String propertyValue) {
        super(String.format("Cannot find %s with %s:%s", resourceName, propertyName, propertyValue));
    }

}
