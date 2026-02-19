package es.um.example.demo.domain.port;

import es.um.example.demo.domain.model.Tarea;
import java.util.List;
import java.util.Optional;

public interface TareaRepository {
    List<Tarea> findAll();
    List<Tarea> findByUsuarioId(String usuarioId);
    Optional<Tarea> findByUuid(String uuid);
    Tarea save(Tarea tarea);
}
