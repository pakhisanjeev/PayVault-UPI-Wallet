package com.wallet.wallet_system.repository;

import com.wallet.wallet_system.entity.UpiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpiUserRepository extends JpaRepository<UpiUser, Long> {
    UpiUser findByMobileNumber(String mobileNumber);
    UpiUser findByUpiId(String upiId);
}