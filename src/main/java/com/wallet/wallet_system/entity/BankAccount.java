package com.wallet.wallet_system.entity;

import jakarta.persistence.*;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolderName;
    private String accountNumber;
    private String linkedMobileNumber; // Is number se UPI link hoga
    private double balance; // Yeh ab sirf Bank/Admin control karega

    // Getters aur Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getLinkedMobileNumber() { return linkedMobileNumber; }
    public void setLinkedMobileNumber(String linkedMobileNumber) { this.linkedMobileNumber = linkedMobileNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}