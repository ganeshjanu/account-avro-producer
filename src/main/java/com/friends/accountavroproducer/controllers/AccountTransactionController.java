package com.friends.accountavroproducer.controllers;

import com.friends.accountservice.AccountTransaction;
import com.friends.accountavroproducer.beans.AccountTransactionRequest;
import com.friends.accountavroproducer.exceptions.AccountServiceException;
import com.friends.accountavroproducer.services.AccountTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountTransactionController {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionController.class);

    @Autowired
    private AccountTransactionService accountTransactionService;


    @PostMapping("/accountTransaction")
    public boolean add(@RequestBody AccountTransactionRequest accountTransactionRequest) throws AccountServiceException {
        AccountTransaction accountTransaction = AccountTransaction.newBuilder()
                .setId(accountTransactionRequest.getId())
                .setAccountId(accountTransactionRequest.getAccountId())
                .build();
        return accountTransactionService.add(accountTransaction);
    }

}
