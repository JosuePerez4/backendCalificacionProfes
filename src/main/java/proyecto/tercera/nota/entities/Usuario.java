package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "correo", length = 100, nullable = false, unique = true)
	private String correo;

	@Column(name = "contraseña", length = 20, nullable = false)
	private String contraseña;

	@Column(name = "rol", nullable = false) // 1 = Administrador, 2 = Estudiante
	private int rol;

	@Column(nullable = true)
	private String tokenRecuperacion;
}
