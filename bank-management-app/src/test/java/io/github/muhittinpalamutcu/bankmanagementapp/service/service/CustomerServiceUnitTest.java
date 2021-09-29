package io.github.muhittinpalamutcu.bankmanagementapp.service.service;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.InputValidationException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceUnitTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

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

        // customerDTOWithWrongSalary
        CustomerDTO customerDTOWithWrongSalary = new CustomerDTO();
        customerDTOWithWrongSalary.setIdentityNumber("164259124290");
        customerDTOWithWrongSalary.setPhoneNumber("05555555555");
        customerDTOWithWrongSalary.setFirstName("John");
        customerDTOWithWrongSalary.setLastName("Doe");
        customerDTOWithWrongSalary.setSalary(new BigDecimal(-10));

        Executable executable3 = () -> customerService.saveCustomer(customerDTOWithWrongSalary);
        assertThrows(InputValidationException.class, executable3);
    }
}