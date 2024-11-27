package proyecto.tercera.nota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estudiante")
public class Estudiante extends Usuario {
	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "codigo", length = 10, unique = true)
	private String codigo;
	
	@Column(name="carrera")
	private String carrera;
}
