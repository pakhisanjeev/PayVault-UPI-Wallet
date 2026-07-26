package com.wallet.wallet_system.controller;

import com.wallet.wallet_system.entity.BankAccount;
import com.wallet.wallet_system.entity.UpiUser;
import com.wallet.wallet_system.repository.BankAccountRepository;
import com.wallet.wallet_system.repository.UpiUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upi")
@CrossOrigin(origins = "*")
public class UpiController {

    @Autowired
    private BankAccountRepository bankRepo;

    @Autowired
    private UpiUserRepository upiRepo;

    // Yeh function app start hote hi Bank Accounts aur Ramesh ka UPI ID dono set kar dega
    @PostConstruct
    public void initDummyData() {
        if (bankRepo.count() == 0) {
            BankAccount b1 = new BankAccount();
            b1.setAccountHolderName("Sanjeev Choudhary"); // Tera Dummy Bank Account
            b1.setAccountNumber("SBI123456789");
            b1.setLinkedMobileNumber("9876543210");
            b1.setBalance(50000.0); // Bank walo ne 50,000 diye hain!
            bankRepo.save(b1);

            BankAccount b2 = new BankAccount();
            b2.setAccountHolderName("Ramesh Store"); // Kisi dukaan wale ka account
            b2.setAccountNumber("HDFC987654321");
            b2.setLinkedMobileNumber("1234567890");
            b2.setBalance(15000.0);
            bankRepo.save(b2);
        }

        // ✨ Yeh naya addition hai: Ramesh Store ko bhi UpiUser table mein save karna zaroori hai!
        if (upiRepo.findByMobileNumber("1234567890") == null) {
            UpiUser ramesh = new UpiUser();
            ramesh.setUserName("Ramesh Store");
            ramesh.setMobileNumber("1234567890");
            ramesh.setUpiId("1234567890@payvault");
            ramesh.setBankAccountNumber("HDFC987654321");
            upiRepo.save(ramesh);
        }
    }

    // Step 1: User App mein Register karega (OTP Verification ke baad)
    @PostMapping("/register")
    public UpiUser registerUser(@RequestParam String name, @RequestParam String mobile) {
        UpiUser user = upiRepo.findByMobileNumber(mobile);
        if (user == null) {
            user = new UpiUser();
            user.setUserName(name);
            user.setMobileNumber(mobile);
            user.setUpiId(mobile + "@payvault"); // UPI ID ban gayi!

            // Check agar bank mein account hai toh link kar do
            BankAccount bankAcc = bankRepo.findByLinkedMobileNumber(mobile);
            if (bankAcc != null) {
                user.setBankAccountNumber(bankAcc.getAccountNumber());
            }
            return upiRepo.save(user);
        }
        return user;
    }

    // Step 2: Payment System (Ek UPI ID se doosre par paisa bhejna)
    @PostMapping("/pay")
    public Map<String, String> sendMoney(@RequestParam String senderMobile, @RequestParam String receiverUpi, @RequestParam double amount) {
        Map<String, String> response = new HashMap<>();

        UpiUser sender = upiRepo.findByMobileNumber(senderMobile);
        UpiUser receiver = upiRepo.findByUpiId(receiverUpi);

        if (sender == null || receiver == null || sender.getBankAccountNumber() == null || receiver.getBankAccountNumber() == null) {
            response.put("status", "Failed");
            response.put("message", "Invalid UPI ID or Bank Not Linked!");
            return response;
        }

        BankAccount senderBank = bankRepo.findByAccountNumber(sender.getBankAccountNumber());
        BankAccount receiverBank = bankRepo.findByAccountNumber(receiver.getBankAccountNumber());

        if (senderBank.getBalance() >= amount) {
            // Bank se paise kate aur doosre mein dale
            senderBank.setBalance(senderBank.getBalance() - amount);
            receiverBank.setBalance(receiverBank.getBalance() + amount);

            bankRepo.save(senderBank);
            bankRepo.save(receiverBank);

            response.put("status", "Success");
            response.put("message", "Payment of ₹" + amount + " successful to " + receiver.getUserName());
        } else {
            response.put("status", "Failed");
            response.put("message", "Insufficient Bank Balance!");
        }
        return response;
    }

    // Bank Balance Check karne ke liye
    @GetMapping("/balance/{mobile}")
    public Map<String, String> getBalance(@PathVariable String mobile) {
        Map<String, String> response = new HashMap<>();
        UpiUser user = upiRepo.findByMobileNumber(mobile);
        if (user != null && user.getBankAccountNumber() != null) {
            BankAccount bankAcc = bankRepo.findByAccountNumber(user.getBankAccountNumber());
            response.put("balance", String.valueOf(bankAcc.getBalance()));
            response.put("upiId", user.getUpiId());
        } else {
            response.put("error", "Bank not linked");
        }
        return response;
    }
}
//package com.wallet.wallet_system.controller;
//
//import com.wallet.wallet_system.entity.BankAccount;
//import com.wallet.wallet_system.entity.UpiUser;
//import com.wallet.wallet_system.repository.BankAccountRepository;
//import com.wallet.wallet_system.repository.UpiUserRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/upi")
//@CrossOrigin(origins = "*")
//public class UpiController {
//
//    @Autowired
//    private BankAccountRepository bankRepo;
//
//    @Autowired
//    private UpiUserRepository upiRepo;
//
//    // Yeh function app start hote hi Database mein 2 Bank Account bana dega
//    @PostConstruct
//    public void initDummyBankAccounts() {
//        if (bankRepo.count() == 0) {
//            BankAccount b1 = new BankAccount();
//            b1.setAccountHolderName("Sanjeev Choudhary"); // Tera Dummy Bank Account
//            b1.setAccountNumber("SBI123456789");
//            b1.setLinkedMobileNumber("9876543210");
//            b1.setBalance(50000.0); // Bank walo ne 50,000 diye hain!
//            bankRepo.save(b1);
//
//            BankAccount b2 = new BankAccount();
//            b2.setAccountHolderName("Ramesh Store"); // Kisi dukaan wale ka account
//            b2.setAccountNumber("HDFC987654321");
//            b2.setLinkedMobileNumber("1234567890");
//            b2.setBalance(15000.0);
//            bankRepo.save(b2);
//        }
//    }
//
//    // Step 1: User App mein Register karega (OTP Verification ke baad)
//    @PostMapping("/register")
//    public UpiUser registerUser(@RequestParam String name, @RequestParam String mobile) {
//        UpiUser user = upiRepo.findByMobileNumber(mobile);
//        if (user == null) {
//            user = new UpiUser();
//            user.setUserName(name);
//            user.setMobileNumber(mobile);
//            user.setUpiId(mobile + "@payvault"); // UPI ID ban gayi!
//
//            // Check agar bank mein account hai toh link kar do
//            BankAccount bankAcc = bankRepo.findByLinkedMobileNumber(mobile);
//            if (bankAcc != null) {
//                user.setBankAccountNumber(bankAcc.getAccountNumber());
//            }
//            return upiRepo.save(user);
//        }
//        return user;
//    }
//
//    // Step 2: Payment System (Ek UPI ID se doosre par paisa bhejna)
//    @PostMapping("/pay")
//    public Map<String, String> sendMoney(@RequestParam String senderMobile, @RequestParam String receiverUpi, @RequestParam double amount) {
//        Map<String, String> response = new HashMap<>();
//
//        UpiUser sender = upiRepo.findByMobileNumber(senderMobile);
//        UpiUser receiver = upiRepo.findByUpiId(receiverUpi);
//
//        if (sender == null || receiver == null || sender.getBankAccountNumber() == null || receiver.getBankAccountNumber() == null) {
//            response.put("status", "Failed");
//            response.put("message", "Invalid UPI ID or Bank Not Linked!");
//            return response;
//        }
//
//        BankAccount senderBank = bankRepo.findByAccountNumber(sender.getBankAccountNumber());
//        BankAccount receiverBank = bankRepo.findByAccountNumber(receiver.getBankAccountNumber());
//
//        if (senderBank.getBalance() >= amount) {
//            // Bank se paise kate aur doosre mein dale
//            senderBank.setBalance(senderBank.getBalance() - amount);
//            receiverBank.setBalance(receiverBank.getBalance() + amount);
//
//            bankRepo.save(senderBank);
//            bankRepo.save(receiverBank);
//
//            response.put("status", "Success");
//            response.put("message", "Payment of ₹" + amount + " successful to " + receiver.getUserName());
//        } else {
//            response.put("status", "Failed");
//            response.put("message", "Insufficient Bank Balance!");
//        }
//        return response;
//    }
//
//    // Bank Balance Check karne ke liye
//    @GetMapping("/balance/{mobile}")
//    public Map<String, String> getBalance(@PathVariable String mobile) {
//        Map<String, String> response = new HashMap<>();
//        UpiUser user = upiRepo.findByMobileNumber(mobile);
//        if (user != null && user.getBankAccountNumber() != null) {
//            BankAccount bankAcc = bankRepo.findByAccountNumber(user.getBankAccountNumber());
//            response.put("balance", String.valueOf(bankAcc.getBalance()));
//            response.put("upiId", user.getUpiId());
//        } else {
//            response.put("error", "Bank not linked");
//        }
//        return response;
//    }
//}