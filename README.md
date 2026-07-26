# 💳 PayVault - Secure UPI & Digital Wallet System

PayVault is a full-stack digital wallet and UPI payment gateway application built to simulate real-world fintech apps like PhonePe and Google Pay. It bridges a modern, responsive mobile-first frontend with a secure Spring Boot backend and MySQL database.

---

## 🚀 Tech Stack

* **Backend:** Java, Spring Boot (Spring Data JPA, REST APIs)
* **Database:** MySQL, Hibernate
* **Frontend:** HTML5, CSS3, JavaScript (Responsive Mobile UI/UX)

---

## ✨ Key Features

1. **Secure Bank Account Linking:** Maps user mobile numbers to underlying bank accounts with simulated verification.
2. **Scan & Pay (QR Code Simulator):** Features an animated, modern scanner interface that automatically detects and verifies merchant UPI IDs.
3. **Robust Transaction Gateway:** Backend-controlled fund transfers that prevent direct client-side balance tampering and ensure data integrity.
4. **Fintech UI/UX Design:** Built with a sleek, premium mobile frame layout inspired by modern neobanks and payment apps.

---

## 🛠️ Project Architecture

* **`UpiController.java` & `WalletController.java`:** Handle core REST endpoints for user registration, balance checks, and secure fund transfers.
* **Entities (`BankAccount`, `UpiUser`):** Managed via JPA/Hibernate with automatic dummy data initialization for instant testing.
* **Frontend (`index.html`):** Single-page application using dynamic DOM manipulation and asynchronous `fetch` APIs to interact smoothly with the backend.

---

## ⚙️ How to Run Locally

To run this project on your local machine, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/PayVault-UPI-Wallet.git](https://github.com/YOUR_USERNAME/PayVault-UPI-Wallet.git)