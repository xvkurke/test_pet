package dev.lynxie.webapi.tracker.exception;

import dev.lynxie.webapi.master.exception.CustomException;

public class ExpenseNotFoundException extends CustomException {

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
