package io.github.muhittinpalamutcu.bankmanagementapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankManagementAppErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
