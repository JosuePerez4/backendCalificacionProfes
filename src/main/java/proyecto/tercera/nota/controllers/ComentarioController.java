package proyecto.tercera.nota.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyecto.tercera.nota.entities.Comentario;
import proyecto.tercera.nota.services.ComentarioServices;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://127.0.0.1:4455")
public class ComentarioController {
	@Autowired
	private ComentarioServices comentarioServices;

	@GetMapping("/{positivo}")
	public List<Comentario> obtenerComentarios(@PathVariable boolean positivo) {
		return comentarioServices.obtenerComentarios(positivo);
	}

	@PostMapping("/guardar")
	public Comentario guardarComentario(@RequestBody Comentario comentario) {
		return comentarioServices.guardarComentario(comentario);
	}
}
