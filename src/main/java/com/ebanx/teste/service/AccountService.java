package com.ebanx.teste.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ebanx.teste.domain.Account;

@Service
public class AccountService {

    public ResponseEntity<Object> deposit(Integer accountId, Integer amount, Map<Integer, Integer> accounts) {
        Integer balance = accounts.getOrDefault(accountId, 0) + amount;
        accounts.put(accountId, balance);
        Map<String, Account> response = new HashMap<>();
        response.put("destination", new Account(accountId, balance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> withdraw(Integer accountId, Integer amount, Map<Integer, Integer> accounts) {
        if (!accounts.containsKey(accountId))
            return ResponseEntity.notFound().build();

        Integer currentBalance = accounts.get(accountId);
        if (currentBalance < amount)
            return ResponseEntity.badRequest().body("Insufficient balance");
        

        currentBalance = currentBalance - amount;
        accounts.put(accountId, currentBalance);
        Map<String, Account> response = new HashMap<>();
        response.put("origin", new Account(accountId, currentBalance));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Object> transfer(Integer origin, Integer destination, Integer amount,
            Map<Integer, Integer> accounts) {
        if (!accounts.containsKey(origin))
            return ResponseEntity.notFound().build();

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
