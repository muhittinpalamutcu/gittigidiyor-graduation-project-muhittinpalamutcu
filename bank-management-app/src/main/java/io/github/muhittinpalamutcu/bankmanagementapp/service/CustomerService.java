package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerAccountAlreadyInDesiredStatusException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerIsNotActiveException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.InputValidationException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(CustomerDTO customerDTO) {
        customerSaveInputValidations(customerDTO);

        Optional<Customer> customerWithSameIdentity = customerRepository.findByIdentityNumber(customerDTO.getIdentityNumber());
        if (customerWithSameIdentity.isPresent()) {
            final String errorMessage = "There is already a customer with identity: " + customerDTO.getIdentityNumber();
            logger.error(errorMessage);
            throw new InputValidationException(errorMessage);
        }

        Customer savedCustomer = customerRepository.save(customerDTO.dtoToCustomer());
        logger.info("New customer saved with id {}", savedCustomer.getId());
        return savedCustomer;
    }

    public Customer findById(long id) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);
        if (maybeCustomer.isPresent()) {
            return maybeCustomer.get();
        } else {
            final String errorMessage = "Customer not found with id: " + id;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }
    }

    public Customer findByIdentity(String identity) {
        Optional<Customer> maybeCustomer = customerRepository.findByIdentityNumber(identity);
        if (maybeCustomer.isPresent()) {
            return maybeCustomer.get();
        } else {
            final String errorMessage = "Customer not found with identity number: " + identity;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }
    }

    public Customer updateCustomerStatus(long id, boolean isActive) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);

        if (maybeCustomer.isEmpty()) {
            final String errorMessage = "Customer not found with id: " + id;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }

        Customer customer = maybeCustomer.get();
        final String status = isActive ? "activated" : "deactivated";

        if (customer.isActive() == isActive) {
            final String errorMessage = "Account with id: " + id + " is already " + status;
            logger.error(errorMessage);
            throw new CustomerAccountAlreadyInDesiredStatusException(errorMessage);
        } else {
            customer.setActive(isActive);
            Customer updatedCustomer = customerRepository.save(customer);
            final String operationResult = "Account with id: " + id + " has been " + status;
            logger.info(operationResult);
            return updatedCustomer;
        }
    }

    public Customer updateCustomerSalary(long id, BigDecimal salary) {
        if (salary.compareTo(BigDecimal.ZERO) < 0) {
            final String errorMessage = "Salary can not be less than 0";
            logger.error(errorMessage);
            throw new InputValidationException(errorMessage);
        }

        Optional<Customer> maybeCustomer = customerRepository.findById(id);

        if (maybeCustomer.isEmpty()) {
            final String errorMessage = "Customer not found with id: " + id;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }

        Customer customer = maybeCustomer.get();

        if (!customer.isActive()) {
            final String errorMessage = "Can not update salary of de-active customer";
            logger.error(errorMessage);
            throw new CustomerIsNotActiveException(errorMessage);
        }

        customer.setSalary(salary);
        return customerRepository.save(customer);
    }

    private void customerSaveInputValidations(CustomerDTO customerDTO) {
        Matcher identityMatcher = Pattern.compile("^[1-9]{1}[0-9]{9}[02468]{1}$")
                .matcher(customerDTO.getIdentityNumber());
        if (!identityMatcher.find()) {
            final String errorMessage = "Identity number is not in the correct format";
            logger.error(errorMessage);
            throw new InputValidationException(errorMessage);
        }

        Matcher phoneNumberMatcher = Pattern.compile("^(05(\\d{9}))$")
                .matcher(customerDTO.getPhoneNumber());
        if (!phoneNumberMatcher.find()) {
            final String errorMessage = "Phone number is not in the correct format";
            logger.error(errorMessage);
            throw new InputValidationException(errorMessage);
        }

        if (customerDTO.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            final String errorMessage = "Salary can't be less than 0";
            logger.error(errorMessage);
            throw new InputValidationException(errorMessage);
        }
    }
}
