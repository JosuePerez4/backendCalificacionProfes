package proyecto.tercera.nota.repositories;

import org.springframework.stereotype.Repository;
import proyecto.tercera.nota.entities.Estudiante;

@Repository
public interface EstudianteRepository extends UsuarioRepository {
	Estudiante findByCodigo(String codigo);
}
