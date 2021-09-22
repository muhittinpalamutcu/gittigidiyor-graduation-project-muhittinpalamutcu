package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.InputValidationException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIntegrationTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void beforeEach() {
        customerRepository.deleteAll();
    }

    @Test
    void saveCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentityNumber("34867685568");
        customerDTO.setPhoneNumber("05231231212");
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setSalary(new BigDecimal(1200));

        Customer result = customerService.saveCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customerDTO.getIdentityNumber(), result.getIdentityNumber());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getSalary(), result.getSalary());
        assertEquals(customerDTO.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    void saveWithSameIdentityNumberShouldThrow() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentityNumber("34867685568");
        customerDTO.setPhoneNumber("05231231212");
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        customerDTO.setSalary(new BigDecimal(1200));

        customerService.saveCustomer(customerDTO);

        // save with same identity number again should throw error
        Executable executable = () -> customerService.saveCustomer(customerDTO);
        assertThrows(InputValidationException.class, executable);
    }

}