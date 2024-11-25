package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "correo", length = 100)
	private String correo;

	@OneToMany(mappedBy = "profesor")
	private List<Encuesta> encuestas;
}
