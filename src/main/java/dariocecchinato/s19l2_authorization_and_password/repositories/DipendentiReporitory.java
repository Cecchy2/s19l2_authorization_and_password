package dariocecchinato.s19l2_authorization_and_password.repositories;

import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendentiReporitory extends JpaRepository<Dipendente, UUID> {

    boolean existsByEmail(String email);
    Optional<Dipendente> findByEmail(String email);

    Optional<Dipendente> findByUsername(String username);


}
