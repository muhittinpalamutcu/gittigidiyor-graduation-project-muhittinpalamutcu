package io.github.muhittinpalamutcu.bankmanagementapp.controller;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplication;
import io.github.muhittinpalamutcu.bankmanagementapp.service.CreditApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-applications")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    public CreditApplicationController(CreditApplicationService creditApplicationService) {
        this.creditApplicationService = creditApplicationService;
    }

    @GetMapping
    public List<CreditApplication> getCreditApplication(@RequestParam String customerIdentityNumber) {
        return creditApplicationService.getCreditApplicationsByCustomerIdentity(customerIdentityNumber);
    }

    @PostMapping
    public CreditApplication newCreditApplication(@RequestBody String customerIdentityNumber) {
        return creditApplicationService.newCreditApplication(customerIdentityNumber);
    }
}
