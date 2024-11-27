package proyecto.tercera.nota.repositories;

import org.springframework.stereotype.Repository;
import proyecto.tercera.nota.entities.Administrador;

@Repository
public interface AdministradorRepository extends UsuarioRepository {
	Administrador findByUsuario(String usuario);
}
