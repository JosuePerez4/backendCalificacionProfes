package proyecto.tercera.nota.services;

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
}
