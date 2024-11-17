package proyecto.tercera.nota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="nombre", length = 100)
	private String nombre;
	@Column(name="correo", length = 100)
	private String correo;
	@Column(name="rol")
	private int rol;
}
