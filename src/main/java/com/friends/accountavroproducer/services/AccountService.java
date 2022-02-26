package com.friends.accountavroproducer.services;

import com.friends.accountavroproducer.controllers.AccountController;
import com.friends.accountservice.Account;
import com.friends.accountavroproducer.exceptions.AccountServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final KafkaTemplate<String, Account> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(KafkaTemplate<String, Account> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${account.topic.name}")
    private String accountTopicName;

    private int counter = 0;

    @Value("${no.of.partitions}")
    private Integer noOfPartitions;

    public boolean add(Account account) throws AccountServiceException {
        try {
            SendResult<String, Account> sendResult = kafkaTemplate.send(buildMsg(account)).get();
            logger.info("Result : " + sendResult.getProducerRecord().toString());
            counter++;
            if(counter >= noOfPartitions) {
                counter = 0;
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AccountServiceException(exception.getMessage());
        }
    }

    private Message<Account> buildMsg(Account account) {
        int partitionId = counter % noOfPartitions;
        logger.info("Partition Id : " + partitionId);
        Message<Account> build = MessageBuilder
                .withPayload(account)
                .setHeader(KafkaHeaders.TOPIC, accountTopicName)
                .setHeader(KafkaHeaders.PARTITION_ID, partitionId)
                .setHeader(KafkaHeaders.MESSAGE_KEY, account.getId())
                .setHeader("MSG_TYPE", Account.class.getName())
                .build();
        return build;
    }
}
