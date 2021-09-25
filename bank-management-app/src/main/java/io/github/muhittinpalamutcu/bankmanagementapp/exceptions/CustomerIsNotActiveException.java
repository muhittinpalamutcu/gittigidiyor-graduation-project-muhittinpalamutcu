package io.github.muhittinpalamutcu.bankmanagementapp.exceptions;

public class CustomerIsNotActiveException extends RuntimeException {
    public CustomerIsNotActiveException(String message) {
        super(message);
    }
}
