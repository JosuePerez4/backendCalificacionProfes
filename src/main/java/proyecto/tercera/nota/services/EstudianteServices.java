package proyecto.tercera.nota.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.EstudianteRepository;
import proyecto.tercera.nota.repositories.MateriaRepository;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class EstudianteServices {

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private MateriaRepository materiaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Estudiante> obtenerEstudiantes() {
		try {
			// Recuperar todos los usuarios y filtrar los estudiantes
			List<Estudiante> estudiantes = estudianteRepository.findAll().stream()
					.filter(usuario -> usuario instanceof Estudiante) // Filtra solo los Estudiantes
					.map(usuario -> (Estudiante) usuario) // Castea cada Usuario a Estudiante
					.collect(Collectors.toList());

			if (estudiantes.isEmpty()) {
				throw new RuntimeException("No se encontraron estudiantes en la base de datos.");
			}

			return estudiantes;

		} catch (Exception e) {
			throw new RuntimeException("Error al obtener los estudiantes: " + e.getMessage(), e);
		}
	}

	public Estudiante obtenerEstudiante(String codigo) {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("El código del estudiante no puede ser nulo o vacío.");
		}

		Estudiante estudiante = estudianteRepository.findByCodigo(codigo);

		if (estudiante != null) {
			return estudiante;
		} else {
			throw new RuntimeException("Estudiante con código " + codigo + " no encontrado.");
		}
	}

	// Generar y guardar token de recuperación
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

	// Validar si el token pertenece a un estudiante
	public boolean validarTokenEstudiante(String token) {
		Estudiante estudiante = (Estudiante) estudianteRepository.findByTokenRecuperacion(token);
		return estudiante != null;
	}

	// Actualizar la contraseña usando un token de recuperación
	public boolean actualizarContrasenaPorToken(String token, String nuevaContrasena) {
		if (nuevaContrasena == null || nuevaContrasena.isEmpty()) {
			throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
		}

		Estudiante estudiante = (Estudiante) estudianteRepository.findByTokenRecuperacion(token);
		if (estudiante != null) {
			estudiante.setContraseña(nuevaContrasena);
			estudiante.setTokenRecuperacion(null); // Invalida el token después de usarlo
			estudianteRepository.save(estudiante);
			return true;
		}
		return false;
	}

	public Estudiante agregarMateria(String codigo, String codigoMateria) {
		// Busca al estudiante por código
		Estudiante estudiante = estudianteRepository.findByCodigo(codigo);

		// Busca la materia por código
		Materia materia = materiaRepository.findByCodigo(codigoMateria);

		// Verifica si la materia existe
		if (materia == null) {
			throw new RuntimeException("Materia no encontrada.");
		}

		// Agrega la materia al estudiante (asegúrate de que ya tenga la relación de
		// ManyToMany bien configurada)
		estudiante.getMaterias().add(materia);

		// Agrega el estudiante a la lista de estudiantes de la materia
		materia.getEstudiantes().add(estudiante); // Esto es clave para mantener la relación bidireccional

		// Guarda el estudiante con la materia añadida
		estudianteRepository.save(estudiante);

		// Guarda la materia si es necesario
		materiaRepository.save(materia);

		return estudiante;
	}

	public Estudiante obtenerEstudiantePorCorreo(String correo) {
		if (correo == null || correo.trim().isEmpty()) {
			throw new IllegalArgumentException("El correo no puede ser nulo o vacío.");
		}

		// Buscar al usuario por correo, ya que Estudiante extiende de Usuario
		Usuario usuario = usuarioRepository.findByCorreo(correo);

		// Verificar si el usuario es una instancia de Estudiante
		if (usuario instanceof Estudiante) {
			return (Estudiante) usuario;
		} else {
			throw new RuntimeException("No se encontró un estudiante con el correo proporcionado.");
		}
	}

}
