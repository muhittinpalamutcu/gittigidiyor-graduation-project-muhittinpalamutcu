package io.github.muhittinpalamutcu.bankmanagementapp.service.service;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditScore;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CreditScoreRepository;
import io.github.muhittinpalamutcu.bankmanagementapp.service.CreditScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditScoreServiceUnitTest {

    @Mock
    CreditScoreRepository creditScoreRepository;

    @InjectMocks
    CreditScoreService creditScoreService;

    @Test
    void checkCustomerCreditScoreShouldInsertAndReturnCorrectResult() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setIdentityNumber("12044599090");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(2500));
        customer.setActive(true);

        when(creditScoreRepository.findCreditScoreByCustomerId(customer.getId())).thenReturn(Optional.empty());

        CreditScore creditScore = new CreditScore();
        creditScore.setCustomer(customer);
        creditScore.setCreditScore(2000);

        when(creditScoreRepository.save(creditScore)).thenReturn(new CreditScore(1L, customer, 2000));

        int score = creditScoreService.checkCustomerCreditScore(customer);
        assertEquals(2000, score);
    }

    @Test
    void checkCustomerCreditScoreShouldUpdateAndReturnCorrectResult() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setIdentityNumber("12044599094");
        customer.setFirstName("Muhittin");
        customer.setLastName("Palamutcu");
        customer.setPhoneNumber("05055552525");
        customer.setSalary(new BigDecimal(9800));
        customer.setActive(true);

        CreditScore existingCreditScore = new CreditScore();
        existingCreditScore.setCustomer(customer);
        existingCreditScore.setCreditScore(2000);
        existingCreditScore.setId(9L);

        when(creditScoreRepository.findCreditScoreByCustomerId(customer.getId())).thenReturn(Optional.of(existingCreditScore));

        CreditScore updatedCreditScore = new CreditScore();
        updatedCreditScore.setCustomer(existingCreditScore.getCustomer());
        updatedCreditScore.setCreditScore(1000);
        updatedCreditScore.setId(existingCreditScore.getId());

        when(creditScoreRepository.save(updatedCreditScore)).thenReturn(updatedCreditScore);

        int score = creditScoreService.checkCustomerCreditScore(customer);
        assertEquals(1000, score);
    }
}
