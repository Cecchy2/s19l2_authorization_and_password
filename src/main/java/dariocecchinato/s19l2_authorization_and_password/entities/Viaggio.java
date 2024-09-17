package dariocecchinato.s19l2_authorization_and_password.entities;

import dariocecchinato.s19l2_authorization_and_password.enums.Stato_Prenotazione;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private  String destinazione;
    private LocalDate dataViaggio;
    @Enumerated(EnumType.STRING)
    private Stato_Prenotazione statoPrenotazione;

    public Viaggio(String destinazione, LocalDate dataViaggio, Stato_Prenotazione statoPrenotazione) {
        this.destinazione = destinazione;
        this.dataViaggio = dataViaggio;
        this.statoPrenotazione = statoPrenotazione;
    }
}
