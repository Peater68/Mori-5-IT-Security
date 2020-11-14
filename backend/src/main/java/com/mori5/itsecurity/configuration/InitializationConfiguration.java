package com.mori5.itsecurity.configuration;

import com.mori5.itsecurity.domain.Role;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class InitializationConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String defaultAdminUsername;
    private final String defaultAdminPassword;

    @Autowired
    public InitializationConfiguration(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder,
                                       @Value("${itsecurity.admin.username}") String defaultAdminUsername,
                                       @Value("${itsecurity.admin.password}") String defaultAdminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminUsername = defaultAdminUsername;
        this.defaultAdminPassword = defaultAdminPassword;
    }

    @PostConstruct
    private void initAdmin() {
        List<User> users = userRepository.findAllByRole(Role.ADMIN);

        if (users.isEmpty()) {
            User user = User.builder()
                    .username(defaultAdminUsername)
                    .password(passwordEncoder.encode(defaultAdminPassword))
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("Email")
                    .isBanned(false)
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
        }

    }

}
