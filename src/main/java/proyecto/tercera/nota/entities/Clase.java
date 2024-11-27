package proyecto.tercera.nota.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}
