package dev.lynxie.webapi.master.exception.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionResponse {

    public ResponseEntity<ApiError> buildResponseEntity(Integer code, String message, Throwable throwable) {
        return new ResponseEntity<>(new ApiError(code, 
                String.format("%s\n%s", message, throwable.getMessage())), 
                HttpStatus.OK
        );
    }
}
