package com.ebanx.teste.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ebanx.teste.domain.Account;
import com.ebanx.teste.domain.Event;
import com.ebanx.teste.service.AccountService;

import org.springframework.stereotype.Controller;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    private Map<Integer, Integer> accounts = new HashMap<>();

    @PostMapping(value = "/reset")
    public ResponseEntity<Object> resete() {
        accounts.clear();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(value = "/event")
    public ResponseEntity<Object> event(@RequestBody Event event) {
        switch (event.getType()) {
            case "deposit":
                return ((AccountService) accountService).deposit(event.getDestination(), event.getAmount(), accounts);
            case "withdraw":
                return ((AccountService) accountService).withdraw(event.getOrigin(), event.getAmount(), accounts);
            case "transfer":
                return ((AccountService) accountService).transfer(event.getOrigin(), event.getDestination(), event.getAmount(), accounts);
            default:
                return ResponseEntity.badRequest().body("Invalid event type");
        }
    }

    @PostMapping(value = "/balance")
    public ResponseEntity<Object> balance(@RequestParam("account_id") Integer accountId) {
        Integer balance = accounts.getOrDefault(accountId, 0);
        return ResponseEntity.status(balance == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(balance);
    }

}
