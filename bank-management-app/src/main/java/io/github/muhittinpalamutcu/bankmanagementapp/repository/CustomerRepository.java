package io.github.muhittinpalamutcu.bankmanagementapp.repository;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByIdentityNumber(String identityNumber);
}
