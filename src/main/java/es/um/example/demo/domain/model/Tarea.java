package es.um.example.demo.domain.model;

import java.time.LocalDateTime;

public class Tarea {

    private String uuid;
    private String asunto;
    private LocalDateTime fecha;
    private EstadoTarea estado;
    private String usuarioId;
    private LocalDateTime fechaResolucion;

    public Tarea() {
    }

    public Tarea(String uuid, String asunto, LocalDateTime fecha, EstadoTarea estado, String usuarioId) {
        this.uuid = uuid;
        this.asunto = asunto;
        this.fecha = fecha;
        this.estado = estado;
        this.usuarioId = usuarioId;
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

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
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
