package io.github.muhittinpalamutcu.bankmanagementapp.entity;

public enum CreditApplicationStatus {
    APPROVED("Approved"), REJECTED("Rejected");

    private String status;

    CreditApplicationStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
