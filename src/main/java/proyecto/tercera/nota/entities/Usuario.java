package proyecto.tercera.nota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
	@Column(name="codigo", length = 10)
	private String codigo;
	@Column(name="contraseña", length = 20)
	private String contraseña;
	@Column(name="carrera", length = 100)
	private String carrera;
	@Column(name="rol")
	private int rol;
}
