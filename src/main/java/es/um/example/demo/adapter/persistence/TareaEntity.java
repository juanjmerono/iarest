package es.um.example.demo.adapter.persistence;

import es.um.example.demo.domain.model.EstadoTarea;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TAREA")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ASUNTO", nullable = false)
    private String asunto;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoTarea estado;

    public TareaEntity() {
    }

    public TareaEntity(String asunto, LocalDate fecha, EstadoTarea estado) {
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
