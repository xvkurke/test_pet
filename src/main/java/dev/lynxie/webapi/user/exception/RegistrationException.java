package dev.lynxie.webapi.user.exception;

import dev.lynxie.webapi.master.exception.CustomException;

public class RegistrationException extends CustomException {
    
    public RegistrationException(String message) {
        super(message);
    }
}
