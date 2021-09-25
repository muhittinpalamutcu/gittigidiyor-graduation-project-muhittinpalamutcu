package io.github.muhittinpalamutcu.bankmanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private CreditApplicationStatus status;

    private BigDecimal creditLimit;
}