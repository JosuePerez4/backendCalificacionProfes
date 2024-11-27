package proyecto.tercera.nota.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.EstudianteDTO;
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
					.map(estudiante -> new EstudianteDTO(
                            estudiante.getCodigo(),
                            estudiante.getNombre(),
                            estudiante.getCorreo()
                    ))
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

}
