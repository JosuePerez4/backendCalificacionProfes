package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.entities.Encuesta;
import proyecto.tercera.nota.services.EncuestaService;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {
	@Autowired
	private EncuestaService encuestaService;

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
}
