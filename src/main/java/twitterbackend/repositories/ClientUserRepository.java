package twitterbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import twitterbackend.entities.ClientUser;

import java.util.Optional;

public interface ClientUserRepository extends CrudRepository<ClientUser, Long> {

    Optional<ClientUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
