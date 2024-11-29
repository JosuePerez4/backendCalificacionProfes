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
import proyecto.tercera.nota.repositories.PreguntaRepository;
import proyecto.tercera.nota.repositories.ProfesorRepository;
import proyecto.tercera.nota.repositories.UsuarioRepository;

@Service
public class AplicacionEncuestaService {

	@Autowired
	private AplicacionEncuestaRepository aplicacionEncuestaRepository;

	@Autowired
	private EncuestaRepository encuestaRepository;

	@Autowired
	private ProfesorRepository profesorRepository;

	@Autowired
	private MateriaRepository materiaRepository;

	@Autowired
	EstudianteRepository estudianteRepository;
	
	@Autowired
	PreguntaRepository preguntaRepository;

	public List<AplicacionEncuesta> listarAplicacionesPorProfesor(Long profesorId) {
		return aplicacionEncuestaRepository.findByProfesorId(profesorId);
	}

	public AplicacionEncuesta guardarAplicacion(AplicacionEncuestaDTO aplicacionDTO) {
		// Crear una nueva AplicacionEncuesta
		AplicacionEncuesta nuevaAplicacion = new AplicacionEncuesta();

		// Buscar el estudiante por su código y asignarlo como usuario
		Estudiante estudiante = estudianteRepository.findByCodigo(aplicacionDTO.getEstudiante());
		if (estudiante == null) {
			throw new RuntimeException("Estudiante no encontrado");
		}
		nuevaAplicacion.setUsuario(estudiante);

		// Buscar el profesor por su nombre
		Profesor profesor = profesorRepository.findByNombre(aplicacionDTO.getProfesor());
		if (profesor == null) {
			throw new RuntimeException("Profesor no encontrado");
		}
		nuevaAplicacion.setProfesor(profesor);

		// Buscar la materia por su nombre
		Materia materia = materiaRepository.findByNombre(aplicacionDTO.getNombreMateria());
		if (materia == null) {
			throw new RuntimeException("Materia no encontrada");
		}
		nuevaAplicacion.setMateria(materia);

		// Buscar la encuesta por ID, si no existe, se crea una nueva
		Encuesta encuesta = encuestaRepository.findById(1);
		if (encuesta == null) {
			// Crear una nueva encuesta si no se encuentra
			encuesta = new Encuesta();

			// Crear preguntas predeterminadas
			List<Pregunta> preguntasPredeterminadas = new ArrayList<>();

			Pregunta pregunta1 = new Pregunta();
			pregunta1.setTexto("¿Cómo calificarías la calidad del contenido?");
			pregunta1.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta1);

			Pregunta pregunta2 = new Pregunta();
			pregunta2.setTexto("¿Cómo calificarías la claridad en las explicaciones?");
			pregunta2.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta2);

			Pregunta pregunta3 = new Pregunta();
			pregunta3.setTexto("¿Cómo calificarías la disponibilidad para ayudar?");
			pregunta3.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta3);

			Pregunta pregunta4 = new Pregunta();
			pregunta4.setTexto("¿Cómo calificarías la puntualidad?");
			pregunta4.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta4);

			Pregunta pregunta5 = new Pregunta();
			pregunta5.setTexto("¿Observaciones positivas?");
			pregunta5.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta5);

			Pregunta pregunta6 = new Pregunta();
			pregunta6.setTexto("¿Observaciones negativas?");
			pregunta6.setEncuesta(encuesta); // Asociamos la pregunta con la encuesta
			preguntasPredeterminadas.add(pregunta6);

			// Asignar las preguntas predeterminadas a la encuesta
			encuesta.setPreguntas(preguntasPredeterminadas);

			// Guardar las preguntas y la encuesta
			// Persistimos primero las preguntas (esto es importante si las preguntas no se
			// guardan automáticamente)
			for (Pregunta pregunta : preguntasPredeterminadas) {
				preguntaRepository.save(pregunta); // Guardamos cada pregunta
			}

			// Ahora guardamos la encuesta (esto guardará también las preguntas debido a la
			// relación)
			encuesta = encuestaRepository.save(encuesta); // Aquí se persisten las preguntas y la encuesta
		}

		// Ahora la encuesta tiene preguntas, por lo que no hay error al acceder a ellas
		if (encuesta.getPreguntas().isEmpty()) {
			throw new RuntimeException("La encuesta no tiene preguntas.");
		}

		nuevaAplicacion.setEncuesta(encuesta);
		nuevaAplicacion.setTextoPositivo(aplicacionDTO.getTextoPositivo());
		nuevaAplicacion.setTextoNegativo(aplicacionDTO.getTextoNegativo());

		// Crear respuestas y asociarlas a las preguntas de la encuesta
		List<Respuesta> respuestas = new ArrayList<>();
		for (int i = 0; i < aplicacionDTO.getRespuestas().size(); i++) {
			Respuesta respuesta = new Respuesta();
			respuesta.setCalificacion(aplicacionDTO.getRespuestas().get(i));

			// Relacionar la respuesta con la pregunta correspondiente
			Pregunta pregunta = encuesta.getPreguntas().get(i);
			respuesta.setPregunta(pregunta);

			// Relacionar la respuesta con la AplicacionEncuesta
			respuesta.setAplicacionEncuesta(nuevaAplicacion);

			respuestas.add(respuesta);
		}

		// Asignar la lista de respuestas a la AplicacionEncuesta
		nuevaAplicacion.setRespuestas(respuestas);

		// Guardar la AplicacionEncuesta (cascada guarda automáticamente las Respuestas)
		return aplicacionEncuestaRepository.save(nuevaAplicacion);
	}

}
