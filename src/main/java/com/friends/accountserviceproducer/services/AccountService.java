package com.friends.accountserviceproducer.services;

import com.friends.accountservice.Account;
import com.friends.accountserviceproducer.exceptions.AccountServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final KafkaTemplate<String, Account> kafkaTemplate;

    @Autowired
    public AccountService(KafkaTemplate<String, Account> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${account.topic.name}")
    private String accountTopicName;

    public boolean add(Account account) throws AccountServiceException {
        try {
            SendResult<String, Account> sendResult = kafkaTemplate.send(accountTopicName, account.getId().toString(), account).get();
            System.out.println("Result : " + sendResult.getProducerRecord().toString());
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AccountServiceException(exception.getMessage());
        }
    }
}
