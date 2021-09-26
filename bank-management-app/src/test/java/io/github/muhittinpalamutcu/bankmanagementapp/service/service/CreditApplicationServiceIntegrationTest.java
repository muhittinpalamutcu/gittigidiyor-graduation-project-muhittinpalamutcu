package io.github.muhittinpalamutcu.bankmanagementapp.service.service;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplication;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplicationStatus;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerIsNotActiveException;
import io.github.muhittinpalamutcu.bankmanagementapp.exceptions.CustomerNotFoundException;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CreditApplicationRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CustomerRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.service.CreditApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditApplicationServiceIntegrationTest {

    @Autowired
    CreditApplicationService creditApplicationService;

    @Autowired
    CreditApplicationRepository creditApplicationRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void beforeEach() {
        creditApplicationRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void newCreditApplicationOk() {
        Customer customer = new Customer();
        customer.setIdentityNumber("12044599084");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(10000));
        customer.setActive(true);

        customerRepository.save(customer);

        // apply for a credit
        CreditApplication creditApplication = creditApplicationService.newCreditApplication(customer.getIdentityNumber());

        assertNotNull(creditApplication);
        assertEquals(creditApplication.getStatus(), CreditApplicationStatus.APPROVED);
        // salary * default credit multiplier 4
        assertEquals(0, creditApplication.getCreditLimit().compareTo(new BigDecimal(40000)));
    }

    @Test
    void newCreditApplicationOk2() {
        Customer customer = new Customer();
        customer.setIdentityNumber("12044599088");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(6500));
        customer.setActive(true);

        customerRepository.save(customer);

        // apply for a credit with salary 6500
        CreditApplication creditApplication = creditApplicationService.newCreditApplication(customer.getIdentityNumber());

        assertNotNull(creditApplication);
        assertEquals(creditApplication.getStatus(), CreditApplicationStatus.APPROVED);
        // Last digit of identity number 8 return credit score 900, which is less than 1000.
        // Salary is more than 5000 should return credit limit 20000.
        assertEquals(0, creditApplication.getCreditLimit().compareTo(new BigDecimal(20000)));

        // apply for a credit with salary 4000
        customer.setSalary(new BigDecimal(4000));
        customerRepository.save(customer);
        CreditApplication creditApplication2 = creditApplicationService.newCreditApplication(customer.getIdentityNumber());

        assertNotNull(creditApplication2);
        assertEquals(creditApplication2.getStatus(), CreditApplicationStatus.APPROVED);
        // Last digit of identity number 8 return credit score 900, which is less than 1000.
        // Salary is less than 5000 should return credit limit 10000.
        assertEquals(0, creditApplication2.getCreditLimit().compareTo(new BigDecimal(10000)));
    }

    @Test
    void creditScoreShouldReturnRejected() {
        Customer customer = new Customer();
        customer.setIdentityNumber("12044599086");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(6500));
        customer.setActive(true);

        customerRepository.save(customer);

        CreditApplication creditApplication = creditApplicationService.newCreditApplication(customer.getIdentityNumber());

        assertNotNull(creditApplication);
        assertEquals(creditApplication.getStatus(), CreditApplicationStatus.REJECTED);
        // Last digit of identity number 6 return credit score 400,
        // which is less than 500 and that means credit application rejected.
        assertEquals(0, creditApplication.getCreditLimit().compareTo(BigDecimal.ZERO));
    }

    @Test
    void checkCreditApplicationsByCustomerIdentity() {
        Customer customerWithCreditApplication = new Customer();
        customerWithCreditApplication.setIdentityNumber("12044599086");
        customerWithCreditApplication.setFirstName("Muhittin");
        customerWithCreditApplication.setLastName("Palamutcu");
        customerWithCreditApplication.setPhoneNumber("05055552525");
        customerWithCreditApplication.setSalary(new BigDecimal(6500));
        customerWithCreditApplication.setActive(true);

        customerRepository.save(customerWithCreditApplication);
        CreditApplication creditApplication = creditApplicationService.newCreditApplication(customerWithCreditApplication.getIdentityNumber());
        CreditApplication creditApplication2 = creditApplicationService.newCreditApplication(customerWithCreditApplication.getIdentityNumber());
        CreditApplication creditApplication3 = creditApplicationService.newCreditApplication(customerWithCreditApplication.getIdentityNumber());

        // result for customerWithCreditApplication
        List<CreditApplication> creditApplications = creditApplicationService.getCreditApplicationsByCustomerIdentity(customerWithCreditApplication.getIdentityNumber());
        assertThat(creditApplications.size()).isEqualTo(3);
        assertTrue(creditApplications.stream().map(CreditApplication::getId).collect(Collectors.toList()).containsAll(Arrays.asList(creditApplication.getId(), creditApplication2.getId(), creditApplication3.getId())));

        Customer customerWithoutCreditApplication = new Customer();
        customerWithoutCreditApplication.setIdentityNumber("13044599084");
        customerWithoutCreditApplication.setFirstName("John");
        customerWithoutCreditApplication.setLastName("Doe");
        customerWithoutCreditApplication.setPhoneNumber("05055552534");
        customerWithoutCreditApplication.setSalary(new BigDecimal(6500));
        customerWithoutCreditApplication.setActive(true);

        customerRepository.save(customerWithoutCreditApplication);

        // result for customerWithoutCreditApplication
        List<CreditApplication> emptyCreditApplications = creditApplicationService.getCreditApplicationsByCustomerIdentity(customerWithoutCreditApplication.getIdentityNumber());
        assertThat(emptyCreditApplications).isEmpty();
    }

    @Test
    void checkCreditApplicationsByWrongCustomerIdentityShouldThrowError() {
        //throw error if identity number is wrong
        Executable executable = () -> creditApplicationService.getCreditApplicationsByCustomerIdentity("wrong-identity");
        assertThrows(CustomerNotFoundException.class, executable);
    }

    @Test
    void newCreditApplicationWithoutCustomerAccount() {
        Executable executable = () -> creditApplicationService.newCreditApplication("no-identity");
        assertThrows(CustomerNotFoundException.class, executable);
    }

    @Test
    void newCreditApplicationWithDeactiveCustomer() {
        Customer customer = new Customer();
        customer.setIdentityNumber("12044599086");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(6500));
        customer.setActive(true);

        Customer savedCustomer = customerRepository.save(customer);
        savedCustomer.setActive(false);
        customerRepository.save(savedCustomer);

        Executable executable = () -> creditApplicationService.newCreditApplication(savedCustomer.getIdentityNumber());
        assertThrows(CustomerIsNotActiveException.class, executable);
    }
}
