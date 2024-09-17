package dariocecchinato.s19l2_authorization_and_password.payloads;

import java.time.LocalDateTime;

public record ErrorPayloadDTO(String message, LocalDateTime errorTime) {
}
