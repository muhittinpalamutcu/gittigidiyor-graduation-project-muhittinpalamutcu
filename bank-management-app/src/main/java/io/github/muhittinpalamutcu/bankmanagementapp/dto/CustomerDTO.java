package io.github.muhittinpalamutcu.bankmanagementapp.dto;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @ApiModelProperty(example = "164259124290")
    @NotNull(message = "Identity number is mandatory")
    private String identityNumber;

    @ApiModelProperty(example = "John")
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @ApiModelProperty(example = "Doe")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @ApiModelProperty(example = "1200")
    @NotNull(message = "Salary name is mandatory")
    private BigDecimal salary;

    @ApiModelProperty(example = "4915253892425")
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    public Customer dtoToCustomer() {
        Customer customer = new Customer();
        customer.setIdentityNumber(this.getIdentityNumber());
        customer.setFirstName(this.getFirstName());
        customer.setLastName(this.getLastName());
        customer.setSalary(this.getSalary());
        customer.setPhoneNumber(this.getPhoneNumber());
        customer.setActive(true);
        return customer;
    }
}
