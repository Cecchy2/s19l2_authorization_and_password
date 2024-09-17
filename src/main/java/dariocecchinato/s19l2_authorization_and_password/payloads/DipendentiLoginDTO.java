package dariocecchinato.s19l2_authorization_and_password.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendentiLoginDTO(
        @NotEmpty(message = "Lo username è obbligatorio")
        String username,
        @NotEmpty(message = "inserisci una password")
        @Size(min = 3, max = 40, message = "La password deve essere compresa tra 3 e 40 caratteri")
        String password) {}
