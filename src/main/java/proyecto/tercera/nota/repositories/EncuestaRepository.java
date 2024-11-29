package proyecto.tercera.nota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Encuesta;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Integer> {
	Encuesta findById(int id);
}
