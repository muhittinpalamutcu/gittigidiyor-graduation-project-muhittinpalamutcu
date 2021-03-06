package io.github.muhittinpalamutcu.bankmanagementapp.controller;

import io.github.muhittinpalamutcu.bankmanagementapp.dto.CustomerDTO;
import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import io.github.muhittinpalamutcu.bankmanagementapp.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public Customer findByIdentity(@RequestParam String identity) {
        return customerService.findByIdentity(identity);
    }

    @PatchMapping("/{id}/status")
    public Customer updateCustomerStatus(@PathVariable long id, @RequestBody boolean isActive) {
        return customerService.updateCustomerStatus(id, isActive);
    }

    @PatchMapping("/{id}/salary")
    public Customer updateCustomerSalary(@PathVariable long id, @RequestBody BigDecimal salary) {
        return customerService.updateCustomerSalary(id, salary);
    }
}
