package com.pickpaysimplified.services;

import com.pickpaysimplified.domain.transaction.Transaction;
import com.pickpaysimplified.domain.user.User;
import com.pickpaysimplified.domain.user.UserType;
import com.pickpaysimplified.dtos.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pickpaysimplified.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransection(TransactionDTO transaction) throws Exception {
        User payer = this.userService.findUserById(transaction.payerId());

        User payee = this.userService.findUserById(transaction.payeeId());

        this.userService.validateTransaction(payer, transaction.value());

        if (!this.authorizeTransaction(payer, payee, transaction.value())) {
            throw new Exception("Transaction not authorized.");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setTemeStamp(LocalDateTime.now());

        payer.setBalance(payer.getBalance().subtract(transaction.value()));
        payee.setBalance(payee.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        this.notificationService.sendNotification(payee,
                "You received a payment of " + transaction.value() + " from " + payer.getFirstName() + ".");
        this.notificationService.sendNotification(payer,
                "Your payment of " + transaction.value() + " to " + payee.getFirstName() + " was successful.");

        return newTransaction;

    }

    public boolean authorizeTransaction(User payer, User payee, BigDecimal amount) {

        ResponseEntity<Map> authorizationResponse = restTemplate
                .getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        Map<String, Object> body = authorizationResponse.getBody();
        Map<String, Object> data = (Map<String, Object>) body.get("data");

        if (body.get("status").equals("success")) {
            return (boolean) data.get("authorized");
        } else {
            return false;
        }
    }

}
