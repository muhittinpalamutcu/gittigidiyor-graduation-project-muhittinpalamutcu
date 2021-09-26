package io.github.muhittinpalamutcu.bankmanagementapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CreditApplication> creditApplications;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    private CreditScore creditScore;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
