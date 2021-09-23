package io.github.muhittinpalamutcu.bankmanagementapp.exceptions;

public class CustomerAccountAlreadyInDesiredStatusException extends RuntimeException {
    public CustomerAccountAlreadyInDesiredStatusException(String message) {
        super(message);
    }
}
