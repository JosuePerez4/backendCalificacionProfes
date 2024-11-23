package proyecto.tercera.nota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proyecto.tercera.nota.entities.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
	boolean existsByUsuario(String usuario);

	Administrador findByUsuario(String usuario);
}
