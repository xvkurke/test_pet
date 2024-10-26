package dev.lynxie.webapi.tracker.exception;

import dev.lynxie.webapi.master.exception.CustomException;

public class CategoryNotFoundException extends CustomException {
    
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
