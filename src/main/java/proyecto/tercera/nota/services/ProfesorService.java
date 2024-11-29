package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.DTO.ProfesorDTO;
import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.entities.Profesor;
import proyecto.tercera.nota.repositories.ProfesorRepository;

@Service
public class ProfesorService {

	@Autowired
	private ProfesorRepository profesorRepository;

	public List<Profesor> listarProfesores() {
		return profesorRepository.findAll();
	}

	public Profesor obtenerProfesorPorId(int id) {
		return profesorRepository.findById(id);
	}

	public ProfesorDTO crearProfesor(ProfesorDTO profesorDTO) {
		// Crear el nuevo profesor desde el DTO
		Profesor profesor = new Profesor();
		profesor.setNombre(profesorDTO.getNombre());
		profesor.setCorreo(profesorDTO.getCorreo());

		// Guardar el profesor en la base de datos
		Profesor profesorGuardado = profesorRepository.save(profesor);

		// Retornar el DTO del profesor guardado
		return new ProfesorDTO(profesorGuardado.getNombre(), profesorGuardado.getCorreo());
	}

	public List<Materia> obtenerMateriasDelProfesor(String correo) {
	    Profesor profesor = profesorRepository.findByCorreo(correo); // Busca el profesor por correo

	    if (profesor != null) {
	        return profesor.getMaterias(); // Retorna las materias del profesor
	    } else {
	        throw new RuntimeException("Profesor con correo " + correo + " no encontrado.");
	    }
	}

}
