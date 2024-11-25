package proyecto.tercera.nota.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.repositories.AdministradorRepository;

@Service
public class AdministradorServices {

	@Autowired
	private AdministradorRepository administradorRepository;

	public Administrador registrarAdministrador(Administrador administrador) {
		String usuario = administrador.getUsuario();
		if (administradorRepository.existsByUsuario(usuario)) {
			throw new IllegalArgumentException("El nombre de usuario " + usuario + " ya está registrado.");
		}
		administrador.setTokenRecuperacion(null); // Garantizar que el token esté vacío
		return administradorRepository.save(administrador);
	}

	public Administrador buscarPorUsuario(String usuario) {
		return administradorRepository.findByUsuario(usuario);
	}

	public String generarYGuardarTokenAdministrador(String usuario) {
		if (usuario == null || usuario.isEmpty()) {
			throw new IllegalArgumentException("El usuario no puede ser nulo o vacío.");
		}

		// Buscar al administrador por su usuario
		Administrador administrador = administradorRepository.findByUsuario(usuario);
		if (administrador == null) {
			throw new IllegalArgumentException("No se encontró un administrador con el usuario proporcionado.");
		}

		// Generar un token único
		String token = UUID.randomUUID().toString();

		try {
			// Guardar el token en el administrador
			administrador.setTokenRecuperacion(token);
			administradorRepository.save(administrador);
			return token; // Devolver el token generado
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el token de recuperación: " + e.getMessage(), e);
		}
	}

	public boolean validarTokenAdministrador(String token) {
		Administrador administrador = administradorRepository.findByTokenRecuperacion(token);
		return administrador != null;
	}

	public boolean actualizarContrasenaPorToken(String token, String nuevaContrasena) {
		Administrador administrador = administradorRepository.findByTokenRecuperacion(token);
		if (administrador != null) {
			administrador.setContraseña(nuevaContrasena);
			administrador.setTokenRecuperacion(null); // Invalida el token después de usarlo
			administradorRepository.save(administrador);
			return true;
		}
		return false;
	}

	public List<Administrador> obtenerAdministradores() {
		try {
			List<Administrador> administradores = administradorRepository.findAll();

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
