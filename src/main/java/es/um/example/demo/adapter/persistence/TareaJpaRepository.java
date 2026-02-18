package es.um.example.demo.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaJpaRepository extends JpaRepository<TareaEntity, Long> {
}
