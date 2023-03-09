package com.example.backend.Services;

import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter

public class AuthenticationStatistics {

    private final UserRepository userRepository;
    private int successfulLogins;
    private int failedLogins;
    private int lockedAccounts;


    @Autowired
    public AuthenticationStatistics(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void incrementSuccessfulLogins() {
        successfulLogins++;
    }

    public void incrementFailedLogins() {
        failedLogins++;
    }

    public void incrementLockedAccounts() {
        lockedAccounts++;
    }

    public int getTotalUserCount() {
        return userRepository.findAll().size();
    }

    public int getLockedUserCount() {
        return userRepository.findByAccountLockedTrue().size();
    }

    public List<String> getTopFailedLoginUsers(int count) {
        return userRepository.findByFailedLoginAttemptsGreaterThanOrderByFailedLoginAttemptsDesc(count)
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }
}
