package es.um.example.demo.application.query;

import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.domain.port.TareaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObtenerTareasQueryHandler {

    private final TareaRepository tareaRepository;

    public ObtenerTareasQueryHandler(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public ObtenerTareasQuery.ObtenerTareasQueryResult handle(ObtenerTareasQuery.ObtenerTareasQueryRequest request, String usuarioId) {
        List<TodoResponse> tareas = tareaRepository.findByUsuarioId(usuarioId).stream()
                .map(tarea -> new TodoResponse(
                        tarea.getUuid(),
                        tarea.getAsunto(),
                        tarea.getFecha(),
                        tarea.getEstado().name(),
                        tarea.getUsuarioId()))
                .toList();
        return new ObtenerTareasQuery.ObtenerTareasQueryResult(tareas);
    }
}
