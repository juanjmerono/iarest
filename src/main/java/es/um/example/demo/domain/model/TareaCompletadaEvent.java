package es.um.example.demo.domain.model;

import java.time.LocalDateTime;

public record TareaCompletadaEvent(
        String uuid,
        String asunto,
        LocalDateTime fechaResolucion,
        String usuarioId
) {
}
