package com.wallet.wallet_system.repository;

import com.wallet.wallet_system.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByLinkedMobileNumber(String mobileNumber);
    BankAccount findByAccountNumber(String accountNumber);
}