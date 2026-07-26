package com.wallet.wallet_system.entity;

import jakarta.persistence.*;

@Entity
public class UpiUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String mobileNumber;
    private String upiId; // Jaise: sanjeev@payvault
    private String bankAccountNumber; // Bank account se jodane ke liye

    // Getters aur Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getUpiId() { return upiId; }
    public void setUpiId(String upiId) { this.upiId = upiId; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }
}