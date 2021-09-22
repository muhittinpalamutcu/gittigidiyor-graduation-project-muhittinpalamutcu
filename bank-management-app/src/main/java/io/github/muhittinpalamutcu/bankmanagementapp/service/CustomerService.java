package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.InputValidationException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        customerSaveValidations(customerDTO);

        Customer savedCustomer = customerRepository.save(customerDTO.dtoToCustomer());
        logger.info("New customer saved with id {}", savedCustomer.getId());
        return savedCustomer;
    }

    private void customerSaveValidations(CustomerDTO customerDTO) {
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
    }

    public Customer findById(long id) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);
        if (maybeCustomer.isPresent()) {
            return maybeCustomer.get();
        } else {
            logger.error("Customer couldn't found with " + id);
            throw new CustomerNotFoundException("Customer couldn't found with " + id);
        }
    }
}
