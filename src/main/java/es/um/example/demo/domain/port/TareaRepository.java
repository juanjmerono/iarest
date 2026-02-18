package es.um.example.demo.domain.port;

import es.um.example.demo.domain.model.Tarea;
import java.util.List;

public interface TareaRepository {
    List<Tarea> findAll();
}
