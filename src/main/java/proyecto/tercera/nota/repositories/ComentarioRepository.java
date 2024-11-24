package proyecto.tercera.nota.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
	List<Comentario> findByPositivo(boolean positivo); // Buscar por tipo de comentario (positivo o negativo)
}
