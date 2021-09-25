package io.github.muhittinpalamutcu.bankmanagementapp.repository;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditScoreRepository extends CrudRepository<CreditScore, Long> {
    @Query(value = "SELECT * FROM credit_score WHERE customer_id = ?1", nativeQuery = true)
    Optional<CreditScore> findCreditScoreByCustomerId(Long customerId);
}
