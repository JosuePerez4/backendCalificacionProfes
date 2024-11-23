package proyecto.tercera.nota.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.services.EstudianteServices;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "http://127.0.0.1:4455") // Permitir solicitudes CORS
public class EstudianteController {

	@Autowired
	private EstudianteServices estudianteServices;

	@PostMapping("/registro")
	public ResponseEntity<?> registrarEstudiante(@RequestBody Estudiante estudiante) {
		try {
			Estudiante estudianteRegistrado = estudianteServices.registrarEstudiante(estudiante);
			return ResponseEntity.ok(estudianteRegistrado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/inicio-sesion")
	public ResponseEntity<?> verificarInicioSesion(@RequestBody Map<String, String> credentials) {
		String codigo = credentials.get("codigo");
		String contraseña = credentials.get("contraseña");

		// Buscar el usuario por código
		Estudiante estudiante = estudianteServices.buscarPorCodigo(codigo);

		// Verificar si el usuario existe y si la contraseña coincide
		if (estudiante != null && estudiante.getContraseña().equals(contraseña)) {
			// Si las credenciales son correctas
			return ResponseEntity.ok(estudiante);
		} else {
			// Si las credenciales no son correctas
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales incorrectas"));
		}
	}
}
