package proyecto.tercera.nota.controllers;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS
public class AdministradorController {

	/*
	 * @PostMapping("/recuperar-contrasena") public ResponseEntity<?>
	 * recuperarContrasenaAdministrador(@RequestBody Map<String, String> datos) {
	 * String usuario = datos.get("usuario"); String email = datos.get("email");
	 * 
	 * try { String token =
	 * administradorServices.generarYGuardarTokenAdministrador(usuario);
	 * 
	 * // Aquí enviarías el correo con el token
	 * emailService.enviarEmailRecuperacion(email, token);
	 * 
	 * return ResponseEntity .ok(Map.of("message",
	 * "Se envió un correo con las instrucciones para recuperar la contraseña.")); }
	 * catch (IllegalArgumentException e) { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",
	 * e.getMessage())); } catch (RuntimeException e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(Map.of("message", "Error al procesar la solicitud.")); } }
	 * 
	 * @PostMapping("/actualizar-contrasena") public ResponseEntity<?>
	 * actualizarContrasena(@RequestBody Map<String, String> datos) { String token =
	 * datos.get("token"); String contraseña = datos.get("nuevaContrasena");
	 * 
	 * try { boolean actualizado =
	 * administradorServices.actualizarContrasenaPorToken(token, contraseña); if
	 * (actualizado) { return ResponseEntity.ok(Map.of("message",
	 * "Contraseña actualizada con éxito.")); } else { return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST) .body(Map.of("message",
	 * "Token inválido o expirado.")); } } catch (RuntimeException e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(Map.of("message", "Error al procesar la solicitud.")); } }
	 * 
	 * @GetMapping("/verificar-administrador") public ResponseEntity<?>
	 * verificarAdministrador(@RequestParam String token) { try { // Verificar el
	 * token y obtener el nombre de usuario de administrador boolean existe =
	 * administradorServices.validarTokenAdministrador(token);
	 * 
	 * if (existe) { // Si se ha encontrado el usuario de administrador, es un
	 * administrador válido return ResponseEntity.ok(Map.of("tipo",
	 * "administrador")); } else { // Si el token no pertenece a un administrador
	 * return ResponseEntity.status(HttpStatus.BAD_REQUEST) .body(Map.of("message",
	 * "Token no válido para administrador.")); } } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body(Map.of("message", "Error al verificar el token del administrador.")); }
	 * }
	 * 
	 * @GetMapping("/administradores") public ResponseEntity<List<Administrador>>
	 * obtenerTodosLosAdministradores() { try { List<Administrador> administradores
	 * = administradorServices.obtenerAdministradores(); return
	 * ResponseEntity.ok(administradores); } catch (RuntimeException e) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); } }
	 * 
	 * @GetMapping("/{usuario}") public ResponseEntity<Administrador>
	 * obtenerAdministradorPorUsuario(@PathVariable String usuario) { try {
	 * Administrador administrador =
	 * administradorServices.obtenerAdministrador(usuario); return
	 * ResponseEntity.ok(administrador); } catch (RuntimeException e) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); } }
	 */

}
