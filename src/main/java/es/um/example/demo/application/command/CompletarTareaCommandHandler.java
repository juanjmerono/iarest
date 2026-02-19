package es.um.example.demo.application.command;

import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.domain.model.EstadoTarea;
import es.um.example.demo.domain.model.Tarea;
import es.um.example.demo.domain.model.TareaCompletadaEvent;
import es.um.example.demo.domain.port.TareaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CompletarTareaCommandHandler {

    private final TareaRepository tareaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CompletarTareaCommandHandler(TareaRepository tareaRepository, ApplicationEventPublisher eventPublisher) {
        this.tareaRepository = tareaRepository;
        this.eventPublisher = eventPublisher;
    }

    public Optional<CompletarTareaCommandResult> handle(CompletarTareaCommand command) {
        Optional<Tarea> optionalTarea = tareaRepository.findByUuid(command.uuid());
        
        if (optionalTarea.isEmpty()) {
            return Optional.empty();
        }

        Tarea tarea = optionalTarea.get();

        if (!tarea.getUsuarioId().equals(command.usuarioId())) {
            return Optional.empty();
        }

        boolean yaCompletada = tarea.getEstado() == EstadoTarea.completada;

        if (!yaCompletada) {
            tarea.setEstado(EstadoTarea.completada);
            tarea.setFechaResolucion(LocalDateTime.now());
            Tarea savedTarea = tareaRepository.save(tarea);

            TareaCompletadaEvent event = new TareaCompletadaEvent(
                    savedTarea.getUuid(),
                    savedTarea.getAsunto(),
                    savedTarea.getFechaResolucion(),
                    savedTarea.getUsuarioId()
            );
            eventPublisher.publishEvent(event);

            return Optional.of(new CompletarTareaCommandResult(
                    savedTarea.getUuid(),
                    savedTarea.getAsunto(),
                    savedTarea.getFecha(),
                    savedTarea.getEstado().name(),
                    savedTarea.getUsuarioId(),
                    savedTarea.getFechaResolucion()
            ));
        }

        return Optional.of(new CompletarTareaCommandResult(
                tarea.getUuid(),
                tarea.getAsunto(),
                tarea.getFecha(),
                tarea.getEstado().name(),
                tarea.getUsuarioId(),
                tarea.getFechaResolucion()
        ));
    }

    public record CompletarTareaCommandResult(
            String uuid,
            String asunto,
            java.time.LocalDateTime fecha,
            String estado,
            String usuarioId,
            java.time.LocalDateTime fechaResolucion
    ) {
        public TodoResponse toTodoResponse() {
            return new TodoResponse(uuid, asunto, fecha, estado, usuarioId, fechaResolucion);
        }
    }
}
