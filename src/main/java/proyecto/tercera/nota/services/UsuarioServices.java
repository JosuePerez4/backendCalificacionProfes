package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.repositories.UsuarioRepository;
import proyecto.tercera.nota.repositories.EstudianteRepository;
import proyecto.tercera.nota.repositories.AdministradorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServices {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private AdministradorRepository administradorRepository;

	// Métodos comunes a todos los usuarios
	public Usuario buscarPorCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}

	public boolean existePorCorreo(String correo) {
		return usuarioRepository.existsByCorreo(correo);
	}

	public Usuario guardarUsuario(Usuario usuario) {
		if (usuario instanceof Estudiante) {
			return estudianteRepository.save((Estudiante) usuario);
		} else if (usuario instanceof Administrador) {
			return administradorRepository.save((Administrador) usuario);
		} else {
			return usuarioRepository.save(usuario); // Para el caso genérico, si necesitas otros usuarios.
		}
	}

	public Optional<Usuario> buscarPorId(int id) {
		return Optional.of(usuarioRepository.findById(id));
	}

	// Métodos específicos para Estudiantes
	public Estudiante buscarEstudiantePorCodigo(String codigo) {
		return estudianteRepository.findByCodigo(codigo);
	}

	public boolean existeEstudiantePorCodigo(Integer id) {
		return estudianteRepository.findById(id) != null;
	}

	// Métodos específicos para Administradores
	public Administrador buscarAdministradorPorUsuario(String usuario) {
		return administradorRepository.findByUsuario(usuario);
	}

	// Método para generar y guardar el token de recuperación de contraseña
	public String generarYGuardarTokenUsuario(String correo) {
		if (correo == null || correo.isEmpty()) {
			throw new IllegalArgumentException("El correo no puede ser nulo o vacío.");
		}

		// Buscar al usuario por su correo
		Usuario usuario = usuarioRepository.findByCorreo(correo);
		if (usuario == null) {
			throw new IllegalArgumentException("No se encontró un usuario con el correo proporcionado.");
		}

		// Generar un token único
		String token = UUID.randomUUID().toString();

		try {
			// Guardar el token en el usuario
			usuario.setTokenRecuperacion(token);
			usuarioRepository.save(usuario);
			return token; // Devolver el token generado
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el token de recuperación: " + e.getMessage(), e);
		}
	}

	// Validar si un token es válido
	public boolean validarTokenUsuario(String token) {
		Usuario usuario = usuarioRepository.findByTokenRecuperacion(token);
		return usuario != null;
	}

	// Actualizar contraseña utilizando el token de recuperación
	public boolean actualizarContrasenaPorToken(String token, String nuevaContrasena) {
		Usuario usuario = usuarioRepository.findByTokenRecuperacion(token);
		if (usuario != null) {
			usuario.setContraseña(nuevaContrasena);
			usuario.setTokenRecuperacion(null); // Invalida el token después de usarlo
			usuarioRepository.save(usuario);
			return true;
		}
		return false;
	}

	// Obtener todos los usuarios
	public List<Usuario> obtenerUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		if (usuarios.isEmpty()) {
			throw new RuntimeException("No se encontraron usuarios.");
		}
		return usuarios;
	}

	// Obtener un usuario por correo
	public Usuario obtenerUsuarioPorCorreo(String correo) {
		Usuario usuario = usuarioRepository.findByCorreo(correo);
		if (usuario == null) {
			throw new RuntimeException("Usuario con correo " + correo + " no encontrado.");
		}
		return usuario;
	}
}
