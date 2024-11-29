package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.services.MateriaService;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

	@Autowired
	private MateriaService materiaService;

	@GetMapping
	public ResponseEntity<List<Materia>> obtenerMaterias() {
		return ResponseEntity.ok(materiaService.obtenerTodasLasMaterias());
	}

	@PostMapping
	public ResponseEntity<Materia> crearMateria(@RequestBody Materia materia) {
		return ResponseEntity.ok(materiaService.guardarMateria(materia));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Materia> obtenerMateriaPorId(@PathVariable int id) {
		return ResponseEntity.ok(materiaService.obtenerMateriaPorId(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarMateria(@PathVariable int id) {
		materiaService.eliminarMateria(id);
		return ResponseEntity.noContent().build();
	}
}
