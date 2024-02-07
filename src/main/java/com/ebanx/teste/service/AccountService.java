package com.ebanx.teste.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ebanx.teste.model.Account;

@Service
public class AccountService {

    public ResponseEntity<Object> deposit(String accountId, Integer amount, Map<String, Integer> accounts) {
        Integer balance = accounts.getOrDefault(accountId, 0) + amount;
        accounts.put(accountId, balance);
        Map<String, Account> response = new HashMap<>();
        response.put("destination", new Account(accountId, balance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> withdraw(String accountId, Integer amount, Map<String, Integer> accounts) {
        if (!accounts.containsKey(accountId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);

        Integer currentBalance = accounts.get(accountId);
        if (currentBalance < amount)
            return ResponseEntity.badRequest().body("Insufficient balance for withdraw");
        

        currentBalance = currentBalance - amount;
        accounts.put(accountId, currentBalance);
        Map<String, Account> response = new HashMap<>();
        response.put("origin", new Account(accountId, currentBalance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> transfer(String origin, String destination, Integer amount,
            Map<String, Integer> accounts) {
        if (!accounts.containsKey(origin))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);

        Integer originBalance = accounts.get(origin);
        if (originBalance < amount)
            return ResponseEntity.badRequest().body("Insufficient balance for transfer");

        accounts.put(origin, originBalance - amount);
        accounts.put(destination, accounts.getOrDefault(destination, 0) + amount);

        Map<String, Account> response = new HashMap<>();
        response.put("origin", new Account(origin, accounts.get(origin)));
        response.put("destination", new Account(destination, accounts.get(destination)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
