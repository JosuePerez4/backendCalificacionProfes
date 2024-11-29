package proyecto.tercera.nota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
	Profesor findById(int id);
}

