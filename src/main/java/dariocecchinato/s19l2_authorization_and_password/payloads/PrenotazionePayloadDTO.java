package dariocecchinato.s19l2_authorization_and_password.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazionePayloadDTO(

        String dettagli,
        @NotNull(message = "Devi inserire l'ID del viaggio")
        UUID viaggio_id,
        @NotNull(message = "Devi inserire l'ID del dipendente")
        UUID dipendente_id) {
}
