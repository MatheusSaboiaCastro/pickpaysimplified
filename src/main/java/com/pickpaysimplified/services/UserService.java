package com.pickpaysimplified.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickpaysimplified.domain.user.User;
import com.pickpaysimplified.domain.user.UserType;
import com.pickpaysimplified.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User payer, BigDecimal amount, String transactionId) throws Exception {
        if (payer.getUserType() == UserType.MERCHANT) {
            throw new Exception("Only personal users can make transactions.");
        }

        if (payer.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("User not found."));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
