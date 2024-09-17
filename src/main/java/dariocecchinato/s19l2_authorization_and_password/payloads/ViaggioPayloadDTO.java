package dariocecchinato.s19l2_authorization_and_password.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioPayloadDTO(
        @NotEmpty(message = "Devi inserire una destinazione")
        String destinazione,
        @NotNull(message = "Devi inserire una data")
        LocalDate dataViaggio,
        String statoPrenotazione
) {}
