package proyecto.tercera.nota.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.services.EmailService;
import proyecto.tercera.nota.services.EstudianteServices;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class EstudianteController {

	@Autowired
	private EstudianteServices estudianteServices;
	@Autowired
	private EmailService emailService;

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

	@PostMapping("/recuperar-contrasena")
	public ResponseEntity<?> recuperarContrasenaEstudiante(@RequestBody Map<String, String> datos) {
		String codigo = datos.get("codigo");
		String email = datos.get("email");

		try {
			String token = estudianteServices.generarYGuardarTokenEstudiante(codigo);

			// Aquí enviarías el correo con el token
			emailService.enviarEmailRecuperacion(email, token);

			return ResponseEntity
					.ok(Map.of("message", "Se envió un correo con las instrucciones para recuperar la contraseña."));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Error al procesar la solicitud."));
		}
	}

	@PostMapping("/actualizar-contrasena")
	public ResponseEntity<?> actualizarContrasena(@RequestBody Map<String, String> datos) {
		String token = datos.get("token");
		String contraseña = datos.get("nuevaContrasena");

		try {
			boolean actualizado = estudianteServices.actualizarContrasenaPorToken(token, contraseña);
			if (actualizado) {
				return ResponseEntity.ok(Map.of("message", "Contraseña actualizada con éxito."));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Map.of("message", "Token inválido o expirado."));
			}
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Error al procesar la solicitud."));
		}
	}

	@GetMapping("/verificar-estudiante")
	public ResponseEntity<?> verificarEstudiante(@RequestParam String token) {
		try {
			// Verificar el token y obtener el código de estudiante
			boolean existe = estudianteServices.validarTokenEstudiante(token);

			if (existe) {
				// Si se ha encontrado el código de estudiante, es un estudiante válido
				return ResponseEntity.ok(Map.of("tipo", "estudiante"));
			} else {
				// Si el token no pertenece a un estudiante
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Map.of("message", "Token no válido para estudiante."));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Error al verificar el token del estudiante."));
		}
	}

}
