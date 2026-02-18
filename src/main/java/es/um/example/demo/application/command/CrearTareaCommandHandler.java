package es.um.example.demo.application.command;

import es.um.example.demo.application.dto.CrearTareaRequest;
import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.domain.model.Tarea;
import es.um.example.demo.domain.port.TareaRepository;
import org.springframework.stereotype.Component;

@Component
public class CrearTareaCommandHandler {

    private final TareaRepository tareaRepository;

    public CrearTareaCommandHandler(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public CrearTareaCommandResult handle(CrearTareaRequest request, String usuarioId) {
        CrearTareaCommand command = CrearTareaCommand.fromRequest(request, usuarioId);
        Tarea tarea = command.toEntity();
        Tarea savedTarea = tareaRepository.save(tarea);
        return new CrearTareaCommandResult(
                savedTarea.getUuid(),
                savedTarea.getAsunto(),
                savedTarea.getFecha(),
                savedTarea.getEstado().name(),
                savedTarea.getUsuarioId()
        );
    }

    public record CrearTareaCommandResult(
            String uuid,
            String asunto,
            java.time.LocalDate fecha,
            String estado,
            String usuarioId
    ) {
        public TodoResponse toTodoResponse() {
            return new TodoResponse(uuid, asunto, fecha, estado, usuarioId);
        }
    }
}
