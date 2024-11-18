package dev.lynxie.webapi.master.exception;

import dev.lynxie.webapi.master.exception.response.ApiErrorCodes;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    private final Integer code;
    private final String details;

    protected CustomException(String message) {
        super(message);
        
        ApiErrorCodes e = ApiErrorCodes.BAD_REQUEST;
        
        this.code = e.getErrorCode();
        this.details = e.toString();
    }
    
    protected CustomException(String message, ApiErrorCodes e) {
        super(message);
        this.code = e.getErrorCode();
        this.details = e.toString();
    }
    
    protected CustomException(String message, ApiErrorCodes e, String details) {
        super(message);
        this.code = e.getErrorCode();
        this.details = details;
    }

    protected CustomException(String message, Integer code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    protected CustomException(String message, ApiErrorCodes e, String details, Throwable cause) {
        super(message, cause);
        this.code = e.getErrorCode();
        this.details = details;
    }
}

