package com.friends.accountavroproducer.services;

import com.friends.accountservice.AccountTransaction;
import com.friends.accountavroproducer.exceptions.AccountServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionService {

    private final KafkaTemplate<String, AccountTransaction> kafkaTemplate;

    @Autowired
    public AccountTransactionService(KafkaTemplate<String, AccountTransaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${accounttransaction.topic.name}")
    private String accounttransactionTopicName;

    public boolean add(AccountTransaction accountTransaction) throws AccountServiceException {
        try {
            SendResult<String, AccountTransaction> sendResult = kafkaTemplate.send(accounttransactionTopicName,
                    accountTransaction.getId().toString(), accountTransaction).get();
            System.out.println("Result : " + sendResult.getProducerRecord().toString());
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AccountServiceException(exception.getMessage());
        }
    }
}
