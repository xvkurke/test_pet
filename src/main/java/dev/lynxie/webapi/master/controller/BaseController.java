package dev.lynxie.webapi.master.controller;

import dev.lynxie.webapi.master.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    public static final String DEFAULT_MESSAGE = "Victory! The operation has wrapped up beautifully.";

    public <T> ResponseEntity<Response> response(String message, Integer code, T object) {
        Response response = new Response()
                .setMessage(message)
                .setCode(code)
                .setData(object);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(code));
    }

    public ResponseEntity<Response> response(String message, Integer code) {
        return response(message, code, null);
    }

    public <T> ResponseEntity<Response> response(T object) {
        return response(DEFAULT_MESSAGE, HttpStatus.OK.value(), object);
    }

    public <T> ResponseEntity<Response> response(Integer code, T object) {
        return response(DEFAULT_MESSAGE, code, object);
    }

    public ResponseEntity<Response> response() {
        return response(null);
    }
}
