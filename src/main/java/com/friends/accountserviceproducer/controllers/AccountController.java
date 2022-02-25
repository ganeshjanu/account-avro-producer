package com.friends.accountserviceproducer.controllers;

import com.friends.accountservice.Account;
import com.friends.accountserviceproducer.beans.AccountRequest;
import com.friends.accountserviceproducer.exceptions.AccountServiceException;
import com.friends.accountserviceproducer.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;


    @PostMapping("/account")
    public boolean add(@RequestBody AccountRequest accountRequest) throws AccountServiceException {
       Account account = Account.newBuilder()
               .setId(accountRequest.getId())
               .setName(accountRequest.getName())
               .build();
        return accountService.add(account);
    }


}
