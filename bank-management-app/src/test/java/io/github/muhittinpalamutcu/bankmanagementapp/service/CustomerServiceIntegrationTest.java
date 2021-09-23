package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerAccountAlreadyInDesiredStatusException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
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

    @Test
    void findByIdentityNumber() {
        final String customerIdentityNumber = "25677685562";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentityNumber(customerIdentityNumber);
        customerDTO.setPhoneNumber("05231231254");
        customerDTO.setFirstName("Muhittin");
        customerDTO.setLastName("Palamutcu");
        customerDTO.setSalary(new BigDecimal(2500));

        Customer savedCustomer = customerService.saveCustomer(customerDTO);

        // find with correct identity number
        Customer foundCustomer = customerService.findByIdentity(customerIdentityNumber);
        assertNotNull(foundCustomer);
        assertEquals(foundCustomer.getId(), savedCustomer.getId());

        // throw if wrong identity number
        Executable executable = () -> customerService.findByIdentity("wrong identity");
        assertThrows(CustomerNotFoundException.class, executable);
    }

    @Test
    void updateCustomerStatus() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentityNumber("25677685562");
        customerDTO.setPhoneNumber("05231231254");
        customerDTO.setFirstName("mui");
        customerDTO.setLastName("coding");
        customerDTO.setSalary(new BigDecimal(5000));

        Customer savedCustomer = customerService.saveCustomer(customerDTO);

        // deactivate customer
        customerService.updateCustomerStatus(savedCustomer.getId(), false);

        // find by id
        Customer deactivatedCustomer = customerService.findById(savedCustomer.getId());

        assertFalse(deactivatedCustomer.isActive());

        // activate customer
        customerService.updateCustomerStatus(savedCustomer.getId(), true);

        // find by id
        Customer activatedCustomer = customerService.findById(savedCustomer.getId());

        assertTrue(activatedCustomer.isActive());
    }

    @Test
    void updateCustomerStatusWithSameStatus() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentityNumber("25677685562");
        customerDTO.setPhoneNumber("05231231254");
        customerDTO.setFirstName("mui");
        customerDTO.setLastName("coding");
        customerDTO.setSalary(new BigDecimal(5000));

        Customer savedCustomer = customerService.saveCustomer(customerDTO);

        // activate customer which is already active should throw error
        Executable executable = () -> customerService.updateCustomerStatus(savedCustomer.getId(), true);
        assertThrows(CustomerAccountAlreadyInDesiredStatusException.class, executable);
    }

}