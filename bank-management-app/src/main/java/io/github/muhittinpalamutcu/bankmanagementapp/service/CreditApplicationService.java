package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplication;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplicationStatus;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerIsNotActiveException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CreditApplicationRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CreditApplicationService {
    private final Logger logger = LoggerFactory.getLogger(CreditApplicationService.class);
    private final BigDecimal DEFAULT_CREDIT_MULTIPLIER = new BigDecimal(4);

    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerRepository customerRepository;
    private final CreditScoreService creditScoreService;

    public CreditApplicationService(CreditApplicationRepository creditApplicationRepository, CustomerRepository customerRepository, CreditScoreService creditScoreService) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.customerRepository = customerRepository;
        this.creditScoreService = creditScoreService;
    }

    public List<CreditApplication> getCreditApplicationsByCustomerIdentity(String customerIdentityNumber) {
        Optional<Customer> maybeCustomer = customerRepository.findByIdentityNumber(customerIdentityNumber);

        if (maybeCustomer.isEmpty()) {
            final String errorMessage = "Customer not found with identity number: " + customerIdentityNumber;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }

        return creditApplicationRepository.findCreditApplicationsByCustomerIdentity(customerIdentityNumber);
    }

    public CreditApplication newCreditApplication(String customerIdentityNumber) {
        logger.info("New credit application received for customer with identity number: {}", customerIdentityNumber);
        Optional<Customer> maybeCustomer = customerRepository.findByIdentityNumber(customerIdentityNumber);

        // validations
        Customer customer = creditApplicationCustomerValidations(customerIdentityNumber, maybeCustomer);

        // check customer score and fill/update score table
        Integer customerCreditScore = creditScoreService.checkCustomerCreditScore(customer);

        // decide application result
        BigDecimal creditResult = calculateCreditResult(customerCreditScore, customer.getSalary());

        // save this application to database
        CreditApplication creditApplication = new CreditApplication();
        creditApplication.setCustomer(customer);
        creditApplication.setStatus(creditResult.compareTo(BigDecimal.ZERO) == 0 ? CreditApplicationStatus.REJECTED : CreditApplicationStatus.APPROVED);
        creditApplication.setCreditLimit(creditResult);
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);

        logger.info("Credit application id {} finalized. Status: {}, Credit Limit: {}", savedCreditApplication.getId(), savedCreditApplication.getStatus(), savedCreditApplication.getCreditLimit());

        return savedCreditApplication;
    }

    private Customer creditApplicationCustomerValidations(String customerIdentityNumber, Optional<Customer> maybeCustomer) {
        // check if customer is exists
        if (maybeCustomer.isEmpty()) {
            final String errorMessage = "Customer not found with identity number: " + customerIdentityNumber;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }

        // check if customer is active
        if (!maybeCustomer.get().isActive()) {
            final String errorMessage = "Can not apply to a credit for de-active customer";
            logger.error(errorMessage);
            throw new CustomerIsNotActiveException(errorMessage);
        }

        return maybeCustomer.get();
    }

    private BigDecimal calculateCreditResult(Integer customerCreditScore, BigDecimal customerSalary) {
        if (customerCreditScore < 500) {
            return BigDecimal.ZERO;
        }

        if (customerCreditScore < 1000) {
            if (customerSalary.compareTo(new BigDecimal(5000)) < 0) {
                return new BigDecimal(10000);
            } else {
                return new BigDecimal(20000);
            }
        }

        return customerSalary.multiply(DEFAULT_CREDIT_MULTIPLIER);
    }
}