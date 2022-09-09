package twitterbackend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import twitterbackend.entities.ClientUser;
import twitterbackend.exceptions.UserNotFoundException;
import twitterbackend.repositories.ClientUserRepository;

@Service
public class PasswordManagementService {

    PasswordEncoder passwordEncoder;
    ClientUserRepository clientUserRepository;

    public PasswordManagementService(PasswordEncoder passwordEncoder, ClientUserRepository clientUserRepository) {

        this.passwordEncoder = passwordEncoder;
        this.clientUserRepository = clientUserRepository;
    }

    public ClientUser changePassword(String username, String newPassword) {

        ClientUser user = clientUserRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        user.setPassword(passwordEncoder.encode(newPassword));
        return clientUserRepository.save(user);
    }

    public boolean validatePassword(String username, String oldPassword) {

        ClientUser user = clientUserRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }
}