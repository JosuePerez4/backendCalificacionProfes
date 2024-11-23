package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Estudiante;
import proyecto.tercera.nota.repositories.EstudianteRepository;

@Service
public class EstudianteServices {

	@Autowired
	private EstudianteRepository estudianteRepository;
	
	public Estudiante registrarEstudiante(Estudiante estudiante) {
		if (estudianteRepository.existsByCodigo(estudiante.getCodigo())) {
			throw new IllegalArgumentException("El código de estudiante ya está registrado.");
		}
		return estudianteRepository.save(estudiante);
	}
	
	public Estudiante buscarPorCodigo(String codigo) {
		return estudianteRepository.findByCodigo(codigo);
	}
}
