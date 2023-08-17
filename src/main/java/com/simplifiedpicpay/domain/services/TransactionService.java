package com.simplifiedpicpay.domain.services;

import com.simplifiedpicpay.domain.dtos.TransactionDTO;
import com.simplifiedpicpay.domain.repositories.TransactionRepository;
import com.simplifiedpicpay.domain.transaction.Transaction;
import com.simplifiedpicpay.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotifyService notifyService;

    @Value("${picpay.url}")
    private String authorizeService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.sender());
        User receiver = this.userService.findUserById(transaction.receiver());

        userService.validateTransaction(sender, transaction.value());
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamps(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notifyService.sendNotification(sender, "Transação realizada com sucesso");
        this.notifyService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authResponse = restTemplate.getForEntity(authorizeService, Map.class);

        if(authResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) Objects.requireNonNull(authResponse.getBody()).get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}