package proyecto.tercera.nota.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.repositories.AdministradorRepository;

@Service
public class AdministradorServices {

	@Autowired
	private AdministradorRepository administradorRepository;

	public Administrador registrarAdministrador(Administrador administrador, Integer id) {
		String usuario = administrador.getUsuario();
		if (administradorRepository.findById(id) != null) {
			throw new IllegalArgumentException("El nombre de usuario " + usuario + " ya está registrado.");
		}
		administrador.setTokenRecuperacion(null); // Garantizar que el token esté vacío
		return administradorRepository.save(administrador);
	}

	public List<Administrador> obtenerAdministradores() {
		try {
			List<Administrador> administradores = administradorRepository.findAll().stream()
					.filter(usuario -> usuario instanceof Administrador) // Filtra solo los Administradores
					.map(usuario -> (Administrador) usuario) // Castea cada Usuario a Administrador
					.collect(Collectors.toList());

			if (administradores.isEmpty()) {
				throw new RuntimeException("No se encontraron administradores en la base de datos.");
			}

			return administradores;

		} catch (Exception e) {
			// Manejo de errores genéricos o específicos
			throw new RuntimeException("Error al obtener los administradores: " + e.getMessage(), e);
		}
	}

	public Administrador obtenerAdministrador(String usuario) {
		if (usuario == null || usuario.trim().isEmpty()) {
			throw new IllegalArgumentException("El usuario no puede ser nulo o vacío.");
		}

		Administrador administrador = administradorRepository.findByUsuario(usuario);

		if (administrador != null) {
			return administrador;
		} else {
			throw new RuntimeException("Administrador con usuario " + usuario + " no encontrado.");
		}
	}

}
