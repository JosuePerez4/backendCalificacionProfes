package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.AplicacionEncuestaDTO;
import proyecto.tercera.nota.entities.AplicacionEncuesta;
import proyecto.tercera.nota.entities.Encuesta;
import proyecto.tercera.nota.entities.Pregunta;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.repositories.EncuestaRepository;
import proyecto.tercera.nota.repositories.ProfesorRepository;

@Service
public class EncuestaService {

	@Autowired
	private EncuestaRepository encuestaRepository;
	
	@Autowired
	private ProfesorRepository profesorRepository;

	public List<Encuesta> listarEncuestas() {
		return encuestaRepository.findAll();
	}

	public Encuesta crearEncuesta(Encuesta encuesta) {
		return encuestaRepository.save(encuesta);
	}

	public Encuesta obtenerEncuestaPorId(int id) {
		return encuestaRepository.findById(id);
	}
	
	public Encuesta obtenerEncuestaPorProfesor(int profesorId) {
	    Profesor profesor = profesorRepository.findById(profesorId);

	    // Asumimos que hay una encuesta genérica para cada profesor
	    return encuestaRepository.findById(profesor.getId());
	}
	public List<Pregunta> obtenerPreguntas(int encuestaId) {
	    Encuesta encuesta = encuestaRepository.findById(encuestaId);
	    return encuesta.getPreguntas();
	}

    public void guardarRespuestas(AplicacionEncuestaDTO aplicacionEncuestaDto) {
        AplicacionEncuesta aplicacionEncuesta = new AplicacionEncuesta();

        // Asignar las relaciones
        aplicacionEncuesta.setUsuario(aplicacionEncuestaDto.getUsuario());
        aplicacionEncuesta.setProfesor(aplicacionEncuestaDto.getProfesor());
        aplicacionEncuesta.setMateria(aplicacionEncuestaDto.getMateria());
        aplicacionEncuesta.setEncuesta(encuestaRepository.findById(1).orElseThrow(() -> new RuntimeException("Encuesta no encontrada")));

        // Guardar las respuestas
        aplicacionEncuesta.setRespuestas(aplicacionEncuestaDto.getRespuestas());
        aplicacionEncuesta.setTextoPositivo(aplicacionEncuestaDto.getTextoPositivo());
        aplicacionEncuesta.setTextoNegativo(aplicacionEncuestaDto.getTextoNegativo());

        aplicacionEncuestaRepository.save(aplicacionEncuesta);
    }
}
