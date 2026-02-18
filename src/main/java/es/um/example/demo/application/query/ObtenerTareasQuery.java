package es.um.example.demo.application.query;

import es.um.example.demo.application.dto.TodoResponse;
import java.util.List;

public class ObtenerTareasQuery {

    public record ObtenerTareasQueryRequest() {}

    public record ObtenerTareasQueryResult(List<TodoResponse> tareas) {}
}
