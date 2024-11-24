package proyecto.tercera.nota.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.repositories.EstudianteRepository;

@Service
public class EstudianteServices {

	@Autowired
	private EstudianteRepository estudianteRepository;

	public Estudiante registrarEstudiante(Estudiante estudiante) {
		if (estudianteRepository.existsByCodigo(estudiante.getCodigo())) {
			throw new IllegalArgumentException("El código de estudiante ya está registrado.");
		}
		estudiante.setTokenRecuperacion(null); // Garantizar que el token esté vacío
		return estudianteRepository.save(estudiante);
	}

	public Estudiante buscarPorCodigo(String codigo) {
		return estudianteRepository.findByCodigo(codigo);
	}

	public String generarYGuardarTokenEstudiante(String codigo) {
		if (codigo == null || codigo.isEmpty()) {
			throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
		}

		// Buscar al estudiante por su código
		Estudiante estudiante = estudianteRepository.findByCodigo(codigo);
		if (estudiante == null) {
			throw new IllegalArgumentException("No se encontró un estudiante con el código proporcionado.");
		}

		// Generar un token único
		String token = UUID.randomUUID().toString();

		try {
			// Guardar el token en el estudiante
			estudiante.setTokenRecuperacion(token);
			estudianteRepository.save(estudiante);
			return token; // Devolver el token generado
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el token de recuperación: " + e.getMessage(), e);
		}
	}

	public boolean validarTokenEstudiante(String token) {
		Estudiante estudiante = estudianteRepository.findByTokenRecuperacion(token);
		return estudiante != null;
	}

	public boolean actualizarContrasenaPorToken(String token, String nuevaContrasena) {
		Estudiante estudiante = estudianteRepository.findByTokenRecuperacion(token);
		if (estudiante != null) {
			estudiante.setContraseña(nuevaContrasena);
			estudiante.setTokenRecuperacion(null); // Invalida el token después de usarlo
			estudianteRepository.save(estudiante);
			return true;
		}
		return false;
	}

}
