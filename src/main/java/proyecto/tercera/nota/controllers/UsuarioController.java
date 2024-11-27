package proyecto.tercera.nota.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.DTO.UsuarioDTO;
import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.services.AutentificacionService;
import proyecto.tercera.nota.services.EmailService;
import proyecto.tercera.nota.services.UsuarioServices;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://127.0.0.1:4455")
public class UsuarioController {

	@Autowired
	private UsuarioServices usuarioServices;

	@Autowired
	private AutentificacionService autentificacion;
	
	@Autowired
	private EmailService emailService;

	// Obtener usuario por ID
	@GetMapping("/{id}")
	public UsuarioDTO obtenerUsuarioPorId(@PathVariable int id) {
		Optional<Usuario> usuario = usuarioServices.buscarPorId(id);
		if (usuario.isPresent()) {
			// Convertir a UsuarioDTO y devolverlo
			return new UsuarioDTO(usuario.get().getId(), usuario.get().getCorreo(), usuario.get().getRol());
		} else {
			// Lanzamos una excepción si no encontramos el usuario, para manejar el error
			throw new RuntimeException("Usuario no encontrado con el ID: " + id);
		}
	}

	// Validar si un correo existe
	@GetMapping("/correo-existe/{correo}")
	public Boolean correoExiste(@PathVariable String correo) {
		return usuarioServices.existePorCorreo(correo);
	}

	// Crear un nuevo usuario (Estudiante o Administrador)
	@PostMapping("/registro")
	public UsuarioDTO crearUsuario(@RequestBody Map<String, Object> usuarioData) {
		try {
			// Extraer el rol del usuario
			Integer rol = (Integer) usuarioData.get("rol");

			Usuario nuevoUsuario;
			if (rol == 2) { // Si es estudiante
				// Crear un objeto Estudiante
				Estudiante estudiante = new Estudiante();
				estudiante.setNombre((String) usuarioData.get("nombre"));
				estudiante.setCorreo((String) usuarioData.get("correo"));
				estudiante.setContraseña((String) usuarioData.get("contraseña"));
				estudiante.setCodigo((String) usuarioData.get("codigo")); // Campo específico
				estudiante.setCarrera((String) usuarioData.get("carrera"));
				estudiante.setRol(2); // Asignar el rol de Estudiante correctamente

				nuevoUsuario = usuarioServices.guardarUsuario(estudiante);

			} else if (rol == 1) { // Si es administrador
				// Crear un objeto Administrador
				Administrador administrador = new Administrador();
				administrador.setCorreo((String) usuarioData.get("correo"));
				administrador.setContraseña((String) usuarioData.get("contraseña"));
				administrador.setUsuario((String) usuarioData.get("usuarioAdmin")); // Campo específico
				administrador.setRol(1); // Asignar el rol de Administrador correctamente

				nuevoUsuario = usuarioServices.guardarUsuario(administrador);

			} else {
				// Si el rol no es válido, lanzar una excepción personalizada
				throw new IllegalArgumentException("Rol inválido. Debe ser 1 (Estudiante) o 2 (Administrador).");
			}

			// Convertir el usuario guardado a un DTO
			return new UsuarioDTO(nuevoUsuario.getId(), nuevoUsuario.getCorreo(), nuevoUsuario.getRol());

		} catch (Exception e) {
			// Si ocurre un error, lanzamos una excepción con un mensaje
			throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
		}
	}

	// Método de inicio de sesión
	@PostMapping("/inicio-sesion")
	public UsuarioDTO inicioSesion(@RequestBody Map<String, String> credentials) {
		String correo = credentials.get("correo");
		String contraseña = credentials.get("contraseña");

		UsuarioDTO usuarioDTO = autentificacion.login(correo, contraseña);
		if (usuarioDTO != null) {
			return usuarioDTO;
		} else {
			throw new RuntimeException("Credenciales incorrectas");
		}
	}

	@PostMapping("/recuperar-contrasena")
	public String recuperarContrasena(@RequestBody Map<String, String> datos) {
		// Validar que el correo esté presente y sea válido
		String correo = datos.get("correo");
		if (correo == null || correo.trim().isEmpty()) {
			throw new IllegalArgumentException("El correo no puede estar vacío.");
		}
		if (!correo.contains("@")) {
			throw new IllegalArgumentException("El correo proporcionado no es válido.");
		}

		try {
			// Buscar el usuario por correo
			Usuario usuario = usuarioServices.buscarPorCorreo(correo);
			if (usuario == null) {
				throw new IllegalArgumentException("No se encontró un usuario con el correo proporcionado.");
			}

			// Generar y guardar el token para recuperación
			String token = usuarioServices.generarYGuardarTokenUsuario(usuario.getCorreo());

			// Enviar el email de recuperación
			emailService.enviarEmailRecuperacion(correo, token);

			return "Se envió un correo de recuperación.";
		} catch (IllegalArgumentException e) {
			// Manejo específico para errores de validación
			throw new IllegalArgumentException("Error de validación: " + e.getMessage(), e);
		} catch (RuntimeException e) {
			// Manejo genérico para otros errores
			throw new RuntimeException("Ocurrió un error al procesar la solicitud: " + e.getMessage(), e);
		}
	}

	@PostMapping("/actualizar-contrasena")
	public String actualizarContrasena(@RequestBody Map<String, String> datos) {
		// Validar que el token y la nueva contraseña estén presentes
		String token = datos.get("token");
		String nuevaContrasena = datos.get("nuevaContrasena");

		if (token == null || token.trim().isEmpty()) {
			throw new IllegalArgumentException("El token no puede estar vacío.");
		}
		if (nuevaContrasena == null || nuevaContrasena.trim().isEmpty()) {
			throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
		}
		if (nuevaContrasena.length() < 8) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
		}

		try {
			// Actualizar la contraseña en base al token
			boolean actualizado = usuarioServices.actualizarContrasenaPorToken(token, nuevaContrasena);

			if (actualizado) {
				return "Contraseña actualizada con éxito.";
			} else {
				return "Token inválido o expirado.";
			}
		} catch (RuntimeException e) {
			// Manejo genérico de excepciones
			throw new RuntimeException("Ocurrió un error al actualizar la contraseña: " + e.getMessage(), e);
		}
	}
}
