package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Comentario;
import proyecto.tercera.nota.repositories.ComentarioRepository;

@Service
public class ComentarioServices {
	@Autowired
	private ComentarioRepository comentarioRepository;

	public List<Comentario> obtenerComentarios(boolean positivo) {
		return comentarioRepository.findByPositivo(positivo);
	}

	public Comentario guardarComentario(Comentario comentario) {
		return comentarioRepository.save(comentario);
	}
}
