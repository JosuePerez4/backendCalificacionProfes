package proyecto.tercera.nota.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.EstudianteDTO;
import proyecto.tercera.nota.DTO.MateriaDTO;
import proyecto.tercera.nota.DTO.ProfesorDTO;
import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.services.EstudianteServices;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class EstudianteController {

	@Autowired
	private EstudianteServices estudianteService;

	@GetMapping
	public List<EstudianteDTO> obtenerTodosLosEstudiantes() {
		try {
			// Obtener la lista de estudiantes desde el servicio
			List<Estudiante> estudiantes = estudianteService.obtenerEstudiantes();

			// Convertir la lista de Estudiantes a una lista de EstudianteDTO
			List<EstudianteDTO> estudiantesDTO = estudiantes.stream()
					.map(estudiante -> new EstudianteDTO(estudiante.getCodigo(), estudiante.getNombre(),
							estudiante.getCorreo()))
					.collect(Collectors.toList());

			return estudiantesDTO; // Devolver la lista de DTOs
		} catch (Exception e) {
			// Manejo genérico de excepciones
			throw new RuntimeException("Ocurrió un error al obtener los estudiantes: " + e.getMessage(), e);
		}
	}

	@GetMapping("/{codigo}")
	public EstudianteDTO obtenerEstudiantePorCodigo(@PathVariable String codigo) {
		// Validar que el código no sea nulo ni vacío
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("El código no puede estar vacío.");
		}

		try {
			// Obtener los datos de un estudiante por su código
			Estudiante estudiante = estudianteService.obtenerEstudiante(codigo);
			if (estudiante == null) {
				throw new IllegalArgumentException("No se encontró un estudiante con el código proporcionado.");
			}

			// Convertir manualmente el objeto Estudiante a EstudianteDTO
			EstudianteDTO estudianteDTO = new EstudianteDTO(estudiante.getCodigo(), estudiante.getNombre(),
					estudiante.getCorreo());
			// Añade más campos si los tienes en EstudianteDTO

			return estudianteDTO; // Devuelve el DTO
		} catch (Exception e) {
			// Manejo genérico de excepciones
			throw new RuntimeException("Ocurrió un error al buscar el estudiante: " + e.getMessage(), e);
		}
	}

		@PostMapping("/{codigoEstudiante}/materias/{materiaId}")
		public ResponseEntity<Estudiante> agregarMateria(@PathVariable String codigoEstudiante,
				@PathVariable String materiaId) {
			Estudiante estudianteActualizado = estudianteService.agregarMateria(codigoEstudiante, materiaId);
			return ResponseEntity.ok(estudianteActualizado);
		}

	// Endpoint para obtener todas las materias de un estudiante
	@GetMapping("/materias/{codigo}")
	public List<MateriaDTO> obtenerMateriasDelEstudiante(@PathVariable String codigo) {
		// Validar que el código no sea nulo ni vacío
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("El código no puede estar vacío.");
		}

		try {
			// Obtener el estudiante por su código
			Estudiante estudiante = estudianteService.obtenerEstudiante(codigo);
			if (estudiante == null) {
				throw new IllegalArgumentException("No se encontró un estudiante con el código proporcionado.");
			}

			// Convertir la lista de Materias del Estudiante a una lista de MateriaDTO
			List<MateriaDTO> materiasDTO = estudiante.getMaterias().stream()
					.map(materia -> new MateriaDTO(materia.getCodigo(), materia.getNombre(),
							materia.getProfesor().getNombre()))
					.collect(Collectors.toList());

			return materiasDTO; // Devolver la lista de materias en formato DTO
		} catch (Exception e) {
			// Manejo genérico de excepciones
			throw new RuntimeException("Ocurrió un error al obtener las materias: " + e.getMessage(), e);
		}
	}

	@GetMapping("/profesores/{codigo}")
	public List<ProfesorDTO> obtenerProfesoresDelEstudiante(@PathVariable String codigo) {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("El código no puede estar vacío.");
		}

		try {
			Estudiante estudiante = estudianteService.obtenerEstudiante(codigo);
			if (estudiante == null) {
				throw new IllegalArgumentException("No se encontró un estudiante con el código proporcionado.");
			}

			// Obtener la lista de profesores asociados a las materias del estudiante
			List<ProfesorDTO> profesoresDTO = estudiante.getMaterias().stream()
					.map(materia -> new ProfesorDTO(materia.getProfesor().getNombre(),
							materia.getProfesor().getCorreo()))
					.distinct() // Eliminar duplicados
					.collect(Collectors.toList());

			return profesoresDTO;
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al obtener los profesores: " + e.getMessage(), e);
		}
	}

	@GetMapping("/codigoPorCorreo/{correo}")
	public ResponseEntity<String> obtenerCodigoPorCorreo(@PathVariable String correo) {
	    if (correo == null || correo.trim().isEmpty()) {
	        throw new IllegalArgumentException("El correo no puede estar vacío.");
	    }

	    try {
	        // Buscar el estudiante por correo
	        Estudiante estudiante = estudianteService.obtenerEstudiantePorCorreo(correo);
	        if (estudiante == null) {
	            throw new IllegalArgumentException("No se encontró un estudiante con el correo proporcionado.");
	        }

	        // Devolver el código del estudiante
	        return ResponseEntity.ok(estudiante.getCodigo());
	    } catch (Exception e) {
	        throw new RuntimeException("Ocurrió un error al buscar el código por correo: " + e.getMessage(), e);
	    }
	}

}
