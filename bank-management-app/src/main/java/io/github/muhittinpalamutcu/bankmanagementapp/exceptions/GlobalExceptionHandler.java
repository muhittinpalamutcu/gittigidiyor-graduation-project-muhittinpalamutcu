package io.github.muhittinpalamutcu.bankmanagementapp.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BankManagementAppErrorResponse> handleException(CustomerNotFoundException exception) {
        BankManagementAppErrorResponse response = prepareErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InputValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BankManagementAppErrorResponse> handleException(InputValidationException exception) {
        BankManagementAppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerAccountAlreadyInDesiredStatusException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BankManagementAppErrorResponse> handleException(CustomerAccountAlreadyInDesiredStatusException exception) {
        BankManagementAppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerIsNotActiveException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BankManagementAppErrorResponse> handleException(CustomerIsNotActiveException exception) {
        BankManagementAppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerOnGoingCreditApplicationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BankManagementAppErrorResponse> handleException(CustomerOnGoingCreditApplicationException exception) {
        BankManagementAppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private BankManagementAppErrorResponse prepareErrorResponse(HttpStatus httpStatus, String message) {
        BankManagementAppErrorResponse response = new BankManagementAppErrorResponse();
        response.setStatus(httpStatus.value());
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
}
