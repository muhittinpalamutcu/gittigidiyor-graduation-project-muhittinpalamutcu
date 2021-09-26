package io.github.muhittinpalamutcu.bankmanagementapp.notification;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;

public interface CustomerNotification {
    void notifyCustomer(Customer customer, String message);
}
