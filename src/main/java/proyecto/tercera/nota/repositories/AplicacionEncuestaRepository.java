package proyecto.tercera.nota.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.AplicacionEncuesta;

@Repository
public interface AplicacionEncuestaRepository extends JpaRepository<AplicacionEncuesta, Integer> {
    List<AplicacionEncuesta> findByProfesorId(Long profesorId);
}
