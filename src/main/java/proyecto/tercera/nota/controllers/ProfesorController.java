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

import proyecto.tercera.nota.DTO.ProfesorDTO;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.services.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class ProfesorController {

	@Autowired
	private ProfesorService profesorService;

	// Método para crear un nuevo profesor
	@PostMapping("/crear")
	public ResponseEntity<ProfesorDTO> crearProfesor(@RequestBody ProfesorDTO profesorDTO) {
		// Llamar al servicio para crear el nuevo profesor
		ProfesorDTO profesorGuardado = profesorService.crearProfesor(profesorDTO);

		// Retornar el profesor creado con el estado HTTP 201 (CREADO)
		return ResponseEntity.status(HttpStatus.CREATED).body(profesorGuardado);
	}

	@GetMapping("/profesor/{correo}/materias")
	public ResponseEntity<List<Materia>> obtenerMateriasDelProfesor(@PathVariable String correo) {
		List<Materia> materias = profesorService.obtenerMateriasDelProfesor(correo);
		return ResponseEntity.ok(materias); // Devuelve las materias del profesor
	}
}
