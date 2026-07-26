package com.wallet.wallet_system.controller;

import com.wallet.wallet_system.entity.Wallet;
import com.wallet.wallet_system.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @PostMapping("/create")
    public Wallet createWallet(@RequestBody Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @GetMapping("/{id}")
    public Wallet getWallet(@PathVariable Long id) {
        return walletRepository.findById(id).orElse(null);
    }

    @PostMapping("/{id}/deposit")
    public Wallet deposit(@PathVariable Long id, @RequestParam double amount) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        if (wallet != null) {
            wallet.setBalance(wallet.getBalance() + amount);
            return walletRepository.save(wallet);
        }
        return null;
    }

    @PostMapping("/{id}/withdraw")
    public Wallet withdraw(@PathVariable Long id, @RequestParam double amount) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        if (wallet != null && wallet.getBalance() >= amount) {
            wallet.setBalance(wallet.getBalance() - amount);
            return walletRepository.save(wallet);
        }
        return null;
    }
}