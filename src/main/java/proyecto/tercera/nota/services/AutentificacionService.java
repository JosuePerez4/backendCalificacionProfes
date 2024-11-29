package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.UsuarioDTO;
import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class AutentificacionService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public UsuarioDTO login(String correo, String contraseña) {
		// Buscar el usuario por correo en la base de datos
		Usuario usuario = usuarioRepository.findByCorreo(correo);

		// Verificar si se encontró el usuario
		if (usuario != null) {
			// Verificar la contraseña
			if (usuario.getContraseña().equals(contraseña)) {
				// Si la contraseña es correcta, devolver el UsuarioDTO
				return new UsuarioDTO(usuario.getId(), usuario.getCorreo(), usuario.getRol());
			}
		}
		// Si no se encuentra el usuario o la contraseña no es correcta, retornar null
		return null;
	}
}
