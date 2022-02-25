package com.friends.accountserviceproducer.exceptions;

public class AccountServiceException extends Exception {

    public AccountServiceException(String errorMsg) {
        super(errorMsg);
    }
}
