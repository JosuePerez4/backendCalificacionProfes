package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Administrador;
import proyecto.tercera.nota.repositories.AdministradorRepository;

@Service
public class AdministradorServices {

	@Autowired
	private AdministradorRepository administradorRepository;
	
	public Administrador registrarAdministrador(Administrador administrador) {
		String usuario = administrador.getUsuario();
		if (administradorRepository.existsByUsuario(usuario)) {
			throw new IllegalArgumentException("El nombre de usuario " + usuario +" ya está registrado.");
		}
		return administradorRepository.save(administrador);
	}
	
	public Administrador buscarPorUsuario(String usuario) {
		return administradorRepository.findByUsuario(usuario);
	}
}
