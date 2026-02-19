package es.um.example.demo.adapter.persistence;

import es.um.example.demo.domain.model.EstadoTarea;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TAREA")
public class TareaEntity {

    @Id
    @Column(name = "UUID", nullable = false)
    private String uuid;

    @Column(name = "ASUNTO", nullable = false)
    private String asunto;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoTarea estado;

    @Column(name = "USUARIO_ID", nullable = false)
    private String usuarioId;

    @Column(name = "FECHA_RESOLUCION")
    private LocalDateTime fechaResolucion;

    public TareaEntity() {
    }

    public TareaEntity(String uuid, String asunto, LocalDateTime fecha, EstadoTarea estado, String usuarioId) {
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
