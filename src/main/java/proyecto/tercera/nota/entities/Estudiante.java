package proyecto.tercera.nota.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "estudiante")
public class Estudiante extends Usuario {
	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "codigo", length = 10, unique = true)
	private String codigo;

	@Column(name = "carrera")
	private String carrera;

	@ManyToMany
	@JoinTable(name = "estudiante_materia", joinColumns = @JoinColumn(name = "estudiante_id"), inverseJoinColumns = @JoinColumn(name = "materia_id"))
	private List<Materia> materias = new ArrayList<Materia>();

}
