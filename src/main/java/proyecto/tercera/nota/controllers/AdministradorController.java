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

import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.services.AdministradorServices;
import proyecto.tercera.nota.services.EmailService;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class AdministradorController {

	@Autowired
	private AdministradorServices administradorServices;
	@Autowired
	private EmailService emailService;

	@PostMapping("/registro")
	public ResponseEntity<?> registrarAdministrador(@RequestBody Administrador admin) {
		try {
			// Verificar que los datos llegan correctamente
			System.out.println("Recibiendo datos: " + admin.toString());

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

	@PostMapping("/recuperar-contrasena")
	public ResponseEntity<?> recuperarContrasenaAdministrador(@RequestBody Map<String, String> datos) {
		String usuario = datos.get("usuario");
		String email = datos.get("email");

		try {
			String token = administradorServices.generarYGuardarTokenAdministrador(usuario);

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
            boolean actualizado = administradorServices.actualizarContrasenaPorToken(token, contraseña);
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
	
	@GetMapping("/verificar-administrador")
	public ResponseEntity<?> verificarAdministrador(@RequestParam String token) {
	    try {
	        // Verificar el token y obtener el nombre de usuario de administrador
	        boolean existe = administradorServices.validarTokenAdministrador(token);

	        if (existe) {
	            // Si se ha encontrado el usuario de administrador, es un administrador válido
	            return ResponseEntity.ok(Map.of("tipo", "administrador"));
	        } else {
	            // Si el token no pertenece a un administrador
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("message", "Token no válido para administrador."));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("message", "Error al verificar el token del administrador."));
	    }
	}

}
