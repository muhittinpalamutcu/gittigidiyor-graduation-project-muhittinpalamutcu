package io.github.muhittinpalamutcu.bankmanagementapp.notification;

import io.github.muhittinpalamutcu.bankmanagementapp.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements CustomerNotification {
    private final Logger logger = LoggerFactory.getLogger(SmsNotificationService.class);

    @Override
    public void notifyCustomer(Customer customer, String message) {
        if (customer.getPhoneNumber() == null) {
            logger.error("Customer phone number is missing to send sms notification");
        } else {
            // send sms message to customer
            logger.info(message);
            logger.info("SMS message {} sent to the customer {}", message, customer.getId());
        }
    }
}
