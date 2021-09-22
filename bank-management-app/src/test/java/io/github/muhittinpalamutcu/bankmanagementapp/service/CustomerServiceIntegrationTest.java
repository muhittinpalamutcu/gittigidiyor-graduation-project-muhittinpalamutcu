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
    void saveCustomerShouldThrowInputValidationException() {
        // customerDTOWithWrongMobile
        CustomerDTO customerDTOWithWrongMobile = new CustomerDTO();
        customerDTOWithWrongMobile.setIdentityNumber("164259124290");
        customerDTOWithWrongMobile.setPhoneNumber("028");
        customerDTOWithWrongMobile.setFirstName("John");
        customerDTOWithWrongMobile.setLastName("Doe");
        customerDTOWithWrongMobile.setSalary(new BigDecimal(1200));

        Executable executable = () -> customerService.saveCustomer(customerDTOWithWrongMobile);
        assertThrows(InputValidationException.class, executable);

        // customerDTOWithWrongIdentityNumber
        CustomerDTO customerDTOWithWrongIdentityNumber = new CustomerDTO();
        customerDTOWithWrongIdentityNumber.setIdentityNumber("001259124295");
        customerDTOWithWrongIdentityNumber.setPhoneNumber("05555555555");
        customerDTOWithWrongIdentityNumber.setFirstName("John");
        customerDTOWithWrongIdentityNumber.setLastName("Doe");
        customerDTOWithWrongIdentityNumber.setSalary(new BigDecimal(1200));

        Executable executable2 = () -> customerService.saveCustomer(customerDTOWithWrongIdentityNumber);
        assertThrows(InputValidationException.class, executable2);
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