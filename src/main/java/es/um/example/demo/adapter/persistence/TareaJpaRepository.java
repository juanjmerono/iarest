package es.um.example.demo.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaJpaRepository extends JpaRepository<TareaEntity, String> {
    List<TareaEntity> findByUsuarioId(String usuarioId);
}
