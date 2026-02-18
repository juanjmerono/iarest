package es.um.example.demo.domain.model;

import java.time.LocalDate;

public class Tarea {

    private Long id;
    private String asunto;
    private LocalDate fecha;
    private EstadoTarea estado;

    public Tarea() {
    }

    public Tarea(Long id, String asunto, LocalDate fecha, EstadoTarea estado) {
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

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }
}
