package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.MateriaDTO;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.repositories.MateriaRepository;
import proyecto.tercera.nota.repositories.ProfesorRepository;

@Service
public class MateriaService {

	@Autowired
	private MateriaRepository materiaRepository;

	@Autowired
	private ProfesorRepository profesorRepository;

	public List<Materia> obtenerTodasLasMaterias() {
		return materiaRepository.findAll();
	}

	public MateriaDTO crearMateria(MateriaDTO materiaDTO) {
		// Buscar el Profesor por su nombre
		Profesor profesor = profesorRepository.findByNombre(materiaDTO.getNombreProfesor());

		// Crear una nueva instancia de Materia
		Materia materia = new Materia();
		materia.setNombre(materiaDTO.getNombre());
		materia.setCodigo(materiaDTO.getCodigo());
		materia.setProfesor(profesor); // Asignar el Profesor a la Materia

		// Guardar la materia en la base de datos
		Materia materiaGuardada = materiaRepository.save(materia);

		// Convertir la entidad en un DTO y devolverlo
		return new MateriaDTO(materiaGuardada.getNombre(), materiaGuardada.getCodigo(),
				materiaGuardada.getProfesor().getNombre());
	}

	public Materia obtenerMateriaPorId(int id) {
		return materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
	}

	public void eliminarMateria(int id) {
		Materia materia = obtenerMateriaPorId(id);
		materiaRepository.delete(materia);
	}
}
