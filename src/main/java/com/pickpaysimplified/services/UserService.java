package com.pickpaysimplified.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickpaysimplified.domain.user.User;
import com.pickpaysimplified.domain.user.UserType;
import com.pickpaysimplified.dtos.UserDTO;
import com.pickpaysimplified.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User payer, BigDecimal amount) throws Exception {
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

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
