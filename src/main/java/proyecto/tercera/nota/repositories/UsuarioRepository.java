package proyecto.tercera.nota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	boolean existsByCodigo(String codigo);
	Usuario findByCodigo(String codigo);
}
