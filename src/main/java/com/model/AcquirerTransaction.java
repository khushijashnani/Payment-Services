package com.model;

import java.io.Serializable;
import java.util.Date;

public class AcquirerTransaction implements Serializable {
    private String id;
    private String customerName, customerCardNumber;
    private String merchantName, merchantAccountId;
    private long amount;
    private String status;
    private long timestamp;

    @Override
    public String toString() {
        return "AcquirerTransaction{" +
                "id='" + id + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerCardNumber='" + customerCardNumber + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAccountId='" + merchantAccountId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCardNumber() {
        return customerCardNumber;
    }

    public void setCustomerCardNumber(String customerCardNumber) {
        this.customerCardNumber = customerCardNumber;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}


