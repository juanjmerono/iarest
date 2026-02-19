package es.um.example.demo.application.command;

import es.um.example.demo.application.dto.CrearTareaRequest;
import es.um.example.demo.domain.model.EstadoTarea;
import es.um.example.demo.domain.model.Tarea;

import java.time.LocalDateTime;
import java.util.UUID;

public record CrearTareaCommand(
        String uuid,
        String asunto,
        LocalDateTime fecha,
        EstadoTarea estado,
        String usuarioId
) {
    public static CrearTareaCommand fromRequest(CrearTareaRequest request, String usuarioId) {
        return new CrearTareaCommand(
                UUID.randomUUID().toString(),
                request.getAsunto(),
                LocalDateTime.now(),
                EstadoTarea.pendiente,
                usuarioId
        );
    }

    public Tarea toEntity() {
        return new Tarea(uuid, asunto, fecha, estado, usuarioId);
    }
}
