package es.um.example.demo.adapter.persistence;

import es.um.example.demo.domain.model.Tarea;
import es.um.example.demo.domain.port.TareaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TareaRepositoryAdapter implements TareaRepository {

    private final TareaJpaRepository jpaRepository;

    public TareaRepositoryAdapter(TareaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Tarea> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    public List<Tarea> findByUsuarioId(String usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Tarea save(Tarea tarea) {
        TareaEntity entity = toEntity(tarea);
        TareaEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    private TareaEntity toEntity(Tarea domain) {
        if (domain.getUuid() != null) {
            TareaEntity entity = new TareaEntity();
            entity.setUuid(domain.getUuid());
            entity.setAsunto(domain.getAsunto());
            entity.setFecha(domain.getFecha());
            entity.setEstado(domain.getEstado());
            entity.setUsuarioId(domain.getUsuarioId());
            return entity;
        }
        return new TareaEntity(
                domain.getUuid(),
                domain.getAsunto(),
                domain.getFecha(),
                domain.getEstado(),
                domain.getUsuarioId()
        );
    }

    private Tarea toDomain(TareaEntity entity) {
        return new Tarea(
                entity.getUuid(),
                entity.getAsunto(),
                entity.getFecha(),
                entity.getEstado(),
                entity.getUsuarioId()
        );
    }
}
