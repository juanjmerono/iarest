package es.um.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Representa una tarea en la lista de TODO")
public class TodoResponse {

    @Schema(description = "Identificador único de la tarea", example = "1")
    private Long id;

    @Schema(description = "Asunto o título de la tarea", example = "Terminar informe mensual")
    private String asunto;

    @Schema(description = "Fecha de la tarea en formato ISO 8601", example = "2025-02-20")
    private LocalDate fecha;

    @Schema(description = "Estado de la tarea", example = "pendiente",
            allowableValues = {"pendiente", "en_progreso", "completada"})
    private String estado;

    public TodoResponse() {
    }

    public TodoResponse(Long id, String asunto, LocalDate fecha, String estado) {
        this.id = id;
        this.asunto = asunto;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
