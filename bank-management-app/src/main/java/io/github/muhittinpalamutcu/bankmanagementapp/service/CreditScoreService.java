package io.github.muhittinpalamutcu.bankmanagementapp.service;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditScore;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.repository.CreditScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditScoreService {
    private final Logger logger = LoggerFactory.getLogger(CreditScoreService.class);

    private final CreditScoreRepository creditScoreRepository;

    public CreditScoreService(CreditScoreRepository creditScoreRepository) {
        this.creditScoreRepository = creditScoreRepository;
    }

    // For each credit application, there is a new credit score check, and it overrides
    // the credit score value in the database
    public int checkCustomerCreditScore(Customer customer) {
        String identityNumber = customer.getIdentityNumber();
        String lastDigitOfIdentityNumber = identityNumber.substring(identityNumber.length() - 1);

        int customerScore = findCustomerCreditScore(Integer.parseInt(lastDigitOfIdentityNumber));

        Optional<CreditScore> maybeCreditScore = creditScoreRepository.findCreditScoreByCustomerId(customer.getId());
        CreditScore creditScore;
        // if there is a credit score for customer update it otherwise insert it to database
        if (maybeCreditScore.isPresent()) {
            creditScore = maybeCreditScore.get();
        } else {
            creditScore = new CreditScore();
            creditScore.setCustomer(customer);
        }
        creditScore.setCreditScore(customerScore);
        creditScoreRepository.save(creditScore);

        logger.info("New credit score {} saved for customer {}", creditScore, customer.getId());
        return customerScore;
    }

    private int findCustomerCreditScore(int lastDigitOfIdentityNumber) {
        switch (lastDigitOfIdentityNumber) {
            case 0: {
                return 2000;
            }
            case 2: {
                return 550;
            }
            case 4: {
                return 1000;
            }
            case 6: {
                return 400;
            }
            case 8: {
                return 900;
            }
            default: {
                return 0;
            }
        }
    }
}
