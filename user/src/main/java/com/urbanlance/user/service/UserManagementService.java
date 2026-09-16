package com.urbanlance.user.service;

import com.urbanlance.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserProfileRepository userProfileRepository;
    private final TransactionTemplate transactionTemplate;
    private final RoutingKafkaTemplate kafkaTemplate;

    public void addProfile(){

    }
}
