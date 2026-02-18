package es.um.example.demo.domain.model;

import java.time.LocalDate;

public class Tarea {

    private String uuid;
    private String asunto;
    private LocalDate fecha;
    private EstadoTarea estado;

    public Tarea() {
    }

    public Tarea(String uuid, String asunto, LocalDate fecha, EstadoTarea estado) {
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

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }
}
