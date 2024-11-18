package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class UsuarioServices {
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario registrarUsuario(Usuario usuario) {
		if (usuarioRepository.existsByCodigo(usuario.getCodigo())) {
			throw new IllegalArgumentException("El código de estudiante ya está registrado.");
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarPorCodigo(String codigo) {
		return usuarioRepository.findByCodigo(codigo);
	}
}
