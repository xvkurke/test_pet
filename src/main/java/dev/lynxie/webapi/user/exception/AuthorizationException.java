package dev.lynxie.webapi.user.exception;

import dev.lynxie.webapi.master.exception.response.ApiErrorCodes;
import dev.lynxie.webapi.master.exception.CustomException;

public abstract class AuthorizationException extends CustomException {

    protected AuthorizationException(String message, String cause) {
        super(message, ApiErrorCodes.AUTHORIZATION_ERROR, cause);
    }

    protected AuthorizationException(String message) {
        super(message, ApiErrorCodes.AUTHORIZATION_ERROR, "");
    }
}