package proyecto.tercera.nota.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.tercera.nota.entities.Materia;
import proyecto.tercera.nota.repositories.MateriaRepository;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Materia> obtenerTodasLasMaterias() {
        return materiaRepository.findAll();
    }

    public Materia guardarMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Materia obtenerMateriaPorId(int id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    }

    public void eliminarMateria(int id) {
        Materia materia = obtenerMateriaPorId(id);
        materiaRepository.delete(materia);
    }
}
