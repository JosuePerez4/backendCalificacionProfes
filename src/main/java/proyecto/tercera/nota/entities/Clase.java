package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Clase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "hora_inicio")
	private String horaInicio;

	@Column(name = "hora_fin")
	private String horaFin;

	@ManyToMany
	@JoinTable(name = "estudiante_clase", joinColumns = @JoinColumn(name = "clase_id"), inverseJoinColumns = @JoinColumn(name = "estudiante_id"))
	private List<Estudiante> estudiantes;

	@ManyToOne
	@JoinColumn(name = "profesor_id")
	private Profesor profesor;
}
