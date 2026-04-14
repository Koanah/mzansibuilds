package com.mzansibuilds.security;

import com.mzansibuilds.entity.User;
import com.mzansibuilds.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        // SecurityContextHolder is where Spring Security stores the logged-in
        // user's session data. getAuthentication().getName() gives us the username.
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Logged-in user not found in database: " + username));
    }
}