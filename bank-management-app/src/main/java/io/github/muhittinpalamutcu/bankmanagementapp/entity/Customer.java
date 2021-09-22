package io.github.muhittinpalamutcu.bankmanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String identityNumber;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String phoneNumber;
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<CreditApplication> creditApplications;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private CreditScore creditScore;
}
