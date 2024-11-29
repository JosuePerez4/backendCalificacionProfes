package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "correo", length = 100)
	private String correo;

	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
	private List<AplicacionEncuesta> aplicaciones;

	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Materia> materias;
}
