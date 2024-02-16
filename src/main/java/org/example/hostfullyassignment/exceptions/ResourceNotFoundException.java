package org.example.hostfullyassignment.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("Resource not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
