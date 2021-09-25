package io.github.muhittinpalamutcu.bankmanagementapp.repository;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.CreditApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplicationRepository extends CrudRepository<CreditApplication, Long> {

    @Query(value = "SELECT ca.* FROM credit_application ca INNER JOIN customer c ON (c.id = ca.customer_id) WHERE c.identity_number = ?1", nativeQuery = true)
    List<CreditApplication> findCreditApplicationsByCustomerIdentity(String customerIdentityNumber);
}
