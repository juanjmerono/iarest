package es.um.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Representa una tarea en la lista de TODO")
public class TodoResponse {

    @Schema(description = "Identificador único universal de la tarea", example = "550e8400-e29b-41d4-a716-446655440000")
    private String uuid;

    @Schema(description = "Asunto o título de la tarea", example = "Terminar informe mensual")
    private String asunto;

    @Schema(description = "Fecha de la tarea en formato ISO 8601", example = "2025-02-20T10:30:00")
    private LocalDateTime fecha;

    @Schema(description = "Estado de la tarea", example = "pendiente",
            allowableValues = {"pendiente", "en_progreso", "completada"})
    private String estado;

    @Schema(description = "Identificador del usuario propietario de la tarea", example = "pepe")
    private String usuarioId;

    @Schema(description = "Fecha de resolución de la tarea en formato ISO 8601", example = "2025-02-20T14:30:00")
    private LocalDateTime fechaResolucion;

    public TodoResponse() {
    }

    public TodoResponse(String uuid, String asunto, LocalDateTime fecha, String estado, String usuarioId, LocalDateTime fechaResolucion) {
        this.uuid = uuid;
        this.asunto = asunto;
        this.fecha = fecha;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.fechaResolucion = fechaResolucion;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}
