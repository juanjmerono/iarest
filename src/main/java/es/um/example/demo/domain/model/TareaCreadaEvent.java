package es.um.example.demo.domain.model;

import java.time.LocalDate;

public record TareaCreadaEvent(
        String uuid,
        String asunto,
        LocalDate fecha,
        String usuarioId
) {
}
