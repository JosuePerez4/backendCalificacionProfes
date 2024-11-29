package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Materia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "codigo")
	private String codigo; // Código único de la materia (opcional, si se requiere)

	@ManyToOne
	@JoinColumn(name = "profesor_id", nullable = false)
	private Profesor profesor;

	@ManyToMany(mappedBy = "materias")
	private List<Usuario> estudiantes; // Los estudiantes matriculados en esta materia

	@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AplicacionEncuesta> aplicacionesEncuestas; // Encuestas aplicadas en esta materia

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
}
