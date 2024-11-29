package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.AplicacionEncuestaDTO;
import proyecto.tercera.nota.entities.AplicacionEncuesta;
import proyecto.tercera.nota.entities.Encuesta;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.AplicacionEncuestaRepository;
import proyecto.tercera.nota.repositories.EncuestaRepository;
import proyecto.tercera.nota.repositories.ProfesorRepository;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class AplicacionEncuestaService {

	@Autowired
	private AplicacionEncuestaRepository aplicacionEncuestaRepository;

	@Autowired
	private EncuestaRepository encuestaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ProfesorRepository profesorRepository;

	public List<AplicacionEncuesta> listarAplicacionesPorProfesor(Long profesorId) {
		return aplicacionEncuestaRepository.findByProfesorId(profesorId);
	}

	public AplicacionEncuesta guardarAplicacion(AplicacionEncuestaDTO aplicacionDTO) {
		// Validar y obtener las entidades necesarias
		Encuesta encuesta = encuestaRepository.findById(aplicacionDTO.getEncuestaId());

		Usuario usuario = usuarioRepository.findById(aplicacionDTO.getUsuarioId());

		Profesor profesor = profesorRepository.findById(aplicacionDTO.getProfesorId());

		// Crear una nueva AplicacionEncuesta y asignar los datos
		AplicacionEncuesta nuevaAplicacion = new AplicacionEncuesta();
		nuevaAplicacion.setEncuesta(encuesta);
		nuevaAplicacion.setUsuario(usuario);
		nuevaAplicacion.setProfesor(profesor);

		// Asignar calificaciones y observaciones
		nuevaAplicacion.setRespuestas(aplicacionDTO.getRespuestas()); // Calificaciones de 1 a 5
		nuevaAplicacion.setTextoPositivo(aplicacionDTO.getTextoPositivo());
		nuevaAplicacion.setTextoNegativo(aplicacionDTO.getTextoNegativo());

		// Guardar en la base de datos
		return aplicacionEncuestaRepository.save(nuevaAplicacion);
	}
}
