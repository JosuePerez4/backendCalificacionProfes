package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.AplicacionEncuestaDTO;
import proyecto.tercera.nota.entities.AplicacionEncuesta;
import proyecto.tercera.nota.entities.Encuesta;
import proyecto.tercera.nota.entities.Pregunta;
import proyecto.tercera.nota.services.AplicacionEncuestaService;
import proyecto.tercera.nota.services.EncuestaService;

@RestController
@RequestMapping("/api/encuestas")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class EncuestaController {
	@Autowired
	private EncuestaService encuestaService;

	@Autowired
	private AplicacionEncuestaService aplicacionEncuestaService;

	@GetMapping
	public List<Encuesta> listarEncuestas() {
		return encuestaService.listarEncuestas();
	}

	@PostMapping
	public Encuesta crearEncuesta(@RequestBody Encuesta encuesta) {
		return encuestaService.crearEncuesta(encuesta);
	}

	@GetMapping("/{id}")
	public Encuesta obtenerEncuesta(@PathVariable int id) {
		return encuestaService.obtenerEncuestaPorId(id);
	}

	@GetMapping("/porProfesor/{profesorId}")
	public ResponseEntity<Encuesta> obtenerEncuestaPorProfesor(@PathVariable int profesorId) {
		Encuesta encuesta = encuestaService.obtenerEncuestaPorProfesor(profesorId);
		return ResponseEntity.ok(encuesta);
	}

	// Endpoint para guardar la aplicación de una encuesta
	@PostMapping("/aplicar")
	public ResponseEntity<?> guardarAplicacion(@RequestBody AplicacionEncuestaDTO aplicacionDTO) {
		try {
			// Verifica que el DTO tenga los datos correctamente
			System.out.println("AplicacionDTO: " + aplicacionDTO);

			// Llamar al servicio para guardar la aplicación de la encuesta
			AplicacionEncuesta nuevaAplicacion = aplicacionEncuestaService.guardarAplicacion(aplicacionDTO);

			// Retornar una respuesta exitosa
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAplicacion);
		} catch (Exception e) {
			// Manejo de errores y retorno de respuesta con el mensaje del error
			System.out.println("Error al guardar la aplicación de la encuesta: " + e.getMessage());

			// Crear un objeto de respuesta con más detalles sobre el error
			String errorResponse = "Error al guardar la aplicación de la encuesta";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/*
	 * @GetMapping("/preguntas") public ResponseEntity<List<Pregunta>>
	 * obtenerPreguntasEncuesta() { List<Pregunta> preguntas =
	 * encuestaService.obtenerPreguntas(); if (preguntas.isEmpty()) { return
	 * ResponseEntity.noContent().build(); } return ResponseEntity.ok(preguntas); }
	 */
}
