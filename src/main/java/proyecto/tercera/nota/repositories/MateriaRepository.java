package proyecto.tercera.nota.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Integer> {
	List<Materia> findByUsuarioId(int usuarioId);
}
