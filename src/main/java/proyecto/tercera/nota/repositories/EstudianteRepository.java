package proyecto.tercera.nota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
	boolean existsByCodigo(String codigo);

	Estudiante findByCodigo(String codigo);
}
