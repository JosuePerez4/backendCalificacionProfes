package proyecto.tercera.nota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "administrador")
public class Administrador extends Usuario {
	@Column(name = "usuario", length = 20, unique = true)
	private String usuario;
}
