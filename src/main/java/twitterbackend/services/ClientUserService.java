package twitterbackend.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import twitterbackend.entities.ClientUser;
import twitterbackend.repositories.ClientUserRepository;

@Service
public class ClientUserService implements UserDetailsService {

    private ClientUserRepository clientUserRepository;
    private PasswordManagementService passwordManagementService;

    public ClientUserService(ClientUserRepository clientUserRepository, PasswordManagementService passwordManagementService) {

        this.clientUserRepository = clientUserRepository;
        this.passwordManagementService = passwordManagementService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return clientUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username"));
    }

    public ClientUser addUser(ClientUser user) {

        String password = passwordManagementService.encodePassword(user.getPassword());
        user.setPassword(password);

        return clientUserRepository.save(user);
    }

    public ClientUser saveUser(ClientUser user) {

        return clientUserRepository.save(user);
    }
}
