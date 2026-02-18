package es.um.example.demo.application.command;

import es.um.example.demo.application.dto.CrearTareaRequest;
import es.um.example.demo.domain.model.EstadoTarea;
import es.um.example.demo.domain.model.Tarea;

import java.time.LocalDate;

public record CrearTareaCommand(
        String asunto,
        LocalDate fecha,
        EstadoTarea estado
) {
    public static CrearTareaCommand fromRequest(CrearTareaRequest request) {
        return new CrearTareaCommand(
                request.getAsunto(),
                LocalDate.now(),
                EstadoTarea.pendiente
        );
    }

    public Tarea toEntity() {
        return new Tarea(null, asunto, fecha, estado);
    }
}
