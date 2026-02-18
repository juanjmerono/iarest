package es.um.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Representa una tarea en la lista de TODO")
public class TodoResponse {

    @Schema(description = "Identificador único universal de la tarea", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uuid;

    @Schema(description = "Asunto o título de la tarea", example = "Terminar informe mensual")
    private String asunto;

    @Schema(description = "Fecha de la tarea en formato ISO 8601", example = "2025-02-20")
    private LocalDate fecha;

    @Schema(description = "Estado de la tarea", example = "pendiente",
            allowableValues = {"pendiente", "en_progreso", "completada"})
    private String estado;

    public TodoResponse() {
    }

    public TodoResponse(String uuid, String asunto, LocalDate fecha, String estado) {
        this.uuid = uuid;
        this.asunto = asunto;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
