package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.DTO.MateriaDTO;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.services.MateriaService;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class MateriaController {

	@Autowired
	private MateriaService materiaService;

	@GetMapping("/materias")
	public ResponseEntity<List<Materia>> obtenerMaterias() {
		return ResponseEntity.ok(materiaService.obtenerTodasLasMaterias());
	}

	@PostMapping("/crear-materia")
	public ResponseEntity<?> crearMateria(@RequestBody MateriaDTO materiaDTO) {
		try {
			// Log para depurar los datos recibidos
			System.out.println("Datos recibidos: " + materiaDTO);

			// Llamar al servicio para crear la materia
			MateriaDTO materiaGuardadaDTO = materiaService.crearMateria(materiaDTO);

			// Retornar la respuesta con el DTO de la materia guardada
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Materia creada con éxito: " + materiaGuardadaDTO.getNombre());
		} catch (Exception e) {
			// Log de error
			e.printStackTrace();

			// Retornar un error específico al cliente
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la materia: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Materia> obtenerMateriaPorId(@PathVariable int id) {
		return ResponseEntity.ok(materiaService.obtenerMateriaPorId(id));
	}

	@GetMapping("/buscar")
	public ResponseEntity<Materia> obtenerMateriaPorNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(materiaService.obtenerMateriaPorNombre(nombre));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarMateria(@PathVariable int id) {
		materiaService.eliminarMateria(id);
		return ResponseEntity.noContent().build();
	}
}
