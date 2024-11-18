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

import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.services.UsuarioServices;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://127.0.0.1:4455") // Permitir solicitudes CORS
public class UsuarioControlador {

	@Autowired
	private UsuarioServices usuarioServices;

	@PostMapping("/registro")
	public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
		try {
			Usuario usuarioRegistrado = usuarioServices.registrarUsuario(usuario);
			return ResponseEntity.ok(usuarioRegistrado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/inicio-sesion")
	public ResponseEntity<?> verificarInicioSesion(@RequestBody Map<String, String> credentials) {
		String codigo = credentials.get("codigo");
		String contraseña = credentials.get("contraseña");

		// Buscar el usuario por código
		Usuario usuario = usuarioServices.buscarPorCodigo(codigo);

		// Verificar si el usuario existe y si la contraseña coincide
		if (usuario != null && usuario.getContraseña().equals(contraseña)) {
			// Si las credenciales son correctas
			return ResponseEntity.ok(usuario);
		} else {
			// Si las credenciales no son correctas
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales incorrectas"));
		}
	}

}
