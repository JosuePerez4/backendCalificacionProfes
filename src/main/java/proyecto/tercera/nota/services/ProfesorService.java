package proyecto.tercera.nota.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.ProfesorDTO;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.ProfesorRepository;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class ProfesorService {

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Profesor> listarProfesores() {
		return profesorRepository.findAll();
	}

	public Profesor obtenerProfesorPorId(int id) {
		return profesorRepository.findById(id);
	}

	public List<ProfesorDTO> obtenerProfesoresPorUsuario(String correo) {
		// Obtener el usuario
		Usuario usuario = usuarioRepository.findByCorreo(correo);

		// Validar si el usuario existe
		if (usuario == null) {
			throw new RuntimeException("Usuario no encontrado con el correo: " + correo);
		}

		// Obtener las materias del usuario (asegúrate de que ya están inicializadas)
		List<Materia> materiasMatriculadas = usuario.getMaterias();

		// Validar que el usuario tenga materias matriculadas
		if (materiasMatriculadas == null || materiasMatriculadas.isEmpty()) {
			return new ArrayList<>(); // Retorna una lista vacía si no tiene materias
		}

		// Obtener los profesores asociados a las materias y eliminar duplicados
		return materiasMatriculadas.stream().map(Materia::getProfesor) // Mapea cada materia al profesor
				.filter(Objects::nonNull) // Filtra profesores nulos
				.distinct() // Elimina duplicados
				.map(profesor -> new ProfesorDTO(profesor.getNombre(), profesor.getCorreo())) // Mapea el profesor a un
																								// DTO
				.collect(Collectors.toList());
	}
}
