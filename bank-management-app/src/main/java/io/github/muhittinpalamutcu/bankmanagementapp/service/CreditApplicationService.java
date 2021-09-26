package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplication;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplicationStatus;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerIsNotActiveException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerOnGoingCreditApplicationException;
import io.github.muhittinpalamutcu.bankmanagementapp.notification.SmsNotificationService;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CreditApplicationRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditApplicationService {
    private final Logger logger = LoggerFactory.getLogger(CreditApplicationService.class);
    private final BigDecimal DEFAULT_CREDIT_MULTIPLIER = new BigDecimal(4);

    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerRepository customerRepository;
    private final CreditScoreService creditScoreService;
    private final SmsNotificationService smsNotificationService;

    public CreditApplicationService(CreditApplicationRepository creditApplicationRepository, CustomerRepository customerRepository, CreditScoreService creditScoreService, SmsNotificationService smsNotificationService) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.customerRepository = customerRepository;
        this.creditScoreService = creditScoreService;
        this.smsNotificationService = smsNotificationService;
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

        // check customer's past applications. do not allow multiple applications in 1 week
        checkCustomerPastApplications(customerIdentityNumber);

        // check customer score and fill/update score table
        Integer customerCreditScore = creditScoreService.checkCustomerCreditScore(customer);

        // decide application result
        BigDecimal creditResult = calculateCreditResult(customerCreditScore, customer.getSalary());

        // save this application to database
        CreditApplication creditApplication = new CreditApplication();
        creditApplication.setCustomer(customer);
        creditApplication.setStatus(creditResult.compareTo(BigDecimal.ZERO) == 0 ? CreditApplicationStatus.REJECTED : CreditApplicationStatus.APPROVED);
        creditApplication.setCreditLimit(creditResult);
        creditApplication.setApplicationDate(LocalDateTime.now());
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);

        notifyCustomerAboutResult(creditApplication);

        logger.info("Credit application id {} finalized. Status: {}, Credit Limit: {}", savedCreditApplication.getId(), savedCreditApplication.getStatus(), savedCreditApplication.getCreditLimit());
        return savedCreditApplication;
    }

    private void checkCustomerPastApplications(String customerIdentityNumber) {
        List<CreditApplication> creditApplications = creditApplicationRepository.findCreditApplicationsByCustomerIdentity(customerIdentityNumber);

        boolean anyApplicationOnLast7Days = creditApplications.stream().anyMatch(c -> c.getApplicationDate().isAfter(LocalDateTime.now().minusDays(7)));
        if (anyApplicationOnLast7Days) {
            final String errorMessage = "Can not proceed, there is an another application on 7 days";
            logger.error(errorMessage);
            throw new CustomerOnGoingCreditApplicationException(errorMessage);
        }
    }

    private void notifyCustomerAboutResult(CreditApplication creditApplication) {
        Customer customer = creditApplication.getCustomer();
        StringBuilder notifyMessage = new StringBuilder();
        notifyMessage.append("Dear ").append(customer.getFullName()).append(", ");
        notifyMessage.append("your credit application has been ");
        notifyMessage.append(creditApplication.getStatus().getStatus()).append(". ");
        if (CreditApplicationStatus.APPROVED.equals(creditApplication.getStatus())) {
            notifyMessage.append("Your credit limit: ");
            notifyMessage.append(creditApplication.getCreditLimit());
            notifyMessage.append(". ");
        }
        notifyMessage.append("Thank you for your application.");

        smsNotificationService.notifyCustomer(customer, notifyMessage.toString());
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