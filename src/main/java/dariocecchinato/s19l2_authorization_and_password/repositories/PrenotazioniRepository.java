package dariocecchinato.s19l2_authorization_and_password.repositories;

import dariocecchinato.s19l2_authorization_and_password.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    boolean existsByDipendenteIdAndViaggioDataViaggio(UUID dipendenteId, LocalDate dataViaggio);

    boolean existsByDipendenteIdAndViaggioDataViaggioAndIdNot(UUID dipendenteId, LocalDate dataViaggio, UUID prenotazioneId);
}
