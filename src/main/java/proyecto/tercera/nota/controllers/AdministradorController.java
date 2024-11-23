package proyecto.tercera.nota.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.services.AdministradorServices;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "http://127.0.0.1:4455") // Permitir solicitudes CORS
public class AdministradorController {

	@Autowired
	private AdministradorServices administradorServices;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registrarAdministrativo (@RequestBody Administrador admin) {
		try {
			Administrador adminRegistrado = administradorServices.registrarAdministrador(admin);
			return ResponseEntity.ok(adminRegistrado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/inicio-sesion")
	public ResponseEntity<?> verificarInicioSesion(@RequestBody Map<String, String> credentials) {
		String usuario = credentials.get("usuario");
		String contraseña = credentials.get("contraseña");

		// Buscar el usuario por código
		Administrador administrador = administradorServices.buscarPorUsuario(usuario);

		// Verificar si el usuario existe y si la contraseña coincide
		if (administrador != null && administrador.getContraseña().equals(contraseña)) {
			// Si las credenciales son correctas
			return ResponseEntity.ok(administrador);
		} else {
			// Si las credenciales no son correctas
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales incorrectas"));
		}
	}
}
