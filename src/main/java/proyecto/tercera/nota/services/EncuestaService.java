package proyecto.tercera.nota.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.AplicacionEncuestaDTO;
import proyecto.tercera.nota.entities.AplicacionEncuesta;
import proyecto.tercera.nota.entities.Encuesta;
import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.entities.Pregunta;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.entities.Respuesta;
import proyecto.tercera.nota.entities.Usuario;
import proyecto.tercera.nota.repositories.AplicacionEncuestaRepository;
import proyecto.tercera.nota.repositories.EncuestaRepository;
import proyecto.tercera.nota.repositories.EstudianteRepository;
import proyecto.tercera.nota.repositories.MateriaRepository;
import proyecto.tercera.nota.repositories.ProfesorRepository;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class EncuestaService {

	@Autowired
	private EncuestaRepository encuestaRepository;

	@Autowired
	private ProfesorRepository profesorRepository;
	
	@Autowired
	private AplicacionEncuestaRepository aplicacionEncuestaRepository;
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	@Autowired
	EstudianteRepository estudianteRepository;

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
		// Crear una nueva instancia de AplicacionEncuesta
		AplicacionEncuesta aplicacionEncuesta = new AplicacionEncuesta();

		// Buscar el usuario (estudiante) por su código
		Estudiante estudiante = estudianteRepository.findByCodigo(aplicacionEncuestaDto.getEstudiante());
		Usuario usuario = (Usuario) estudiante;
		aplicacionEncuesta.setUsuario(usuario);

		// Buscar el profesor por su nombre
		Profesor profesor = profesorRepository.findByNombre(aplicacionEncuestaDto.getProfesor());
		aplicacionEncuesta.setProfesor(profesor);

		// Buscar la materia por su código
		Materia materia = materiaRepository.findByNombre(aplicacionEncuestaDto.getNombreMateria());
		aplicacionEncuesta.setMateria(materia);

		// Obtener la encuesta (en este caso, una encuesta predeterminada con ID 1)
		Encuesta encuesta = encuestaRepository.findById(1);
		aplicacionEncuesta.setEncuesta(encuesta);

		// Asignar los textos abiertos (positivo y negativo)
		aplicacionEncuesta.setTextoPositivo(aplicacionEncuestaDto.getTextoPositivo());
		aplicacionEncuesta.setTextoNegativo(aplicacionEncuestaDto.getTextoNegativo());

		// Crear la lista de Respuestas asociadas a esta AplicacionEncuesta
		List<Respuesta> respuestas = new ArrayList<>();

		// Iterar sobre las respuestas proporcionadas en el DTO
		for (int i = 0; i < aplicacionEncuestaDto.getRespuestas().size(); i++) {
			Respuesta respuesta = new Respuesta();

			// Establecer la calificación numérica
			respuesta.setCalificacion(aplicacionEncuestaDto.getRespuestas().get(i));

			// Relacionar la respuesta con la pregunta correspondiente
			// Suponemos que las preguntas están en el mismo orden en el DTO y en la
			// Encuesta
			Pregunta pregunta = encuesta.getPreguntas().get(i);
			respuesta.setPregunta(pregunta);

			// Relacionar la respuesta con la AplicacionEncuesta
			respuesta.setAplicacionEncuesta(aplicacionEncuesta);

			// Añadir la respuesta a la lista
			respuestas.add(respuesta);
		}

		// Asignar la lista de Respuestas a la AplicacionEncuesta
		aplicacionEncuesta.setRespuestas(respuestas);

		// Guardar la AplicacionEncuesta (cascada guarda automáticamente las Respuestas)
		aplicacionEncuestaRepository.save(aplicacionEncuesta);
	}

}
