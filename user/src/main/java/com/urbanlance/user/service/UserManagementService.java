package com.urbanlance.user.service;

import com.urbanlance.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserProfileRepository userProfileRepository;

}
