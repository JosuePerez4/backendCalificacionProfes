package proyecto.tercera.nota.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "comentarios")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(nullable = false)
	private String texto; // El contenido del comentario

	@Column(nullable = false)
	private boolean positivo; // Indica si es positivo o negativo

	@Column(nullable = false)
	private LocalDateTime fechaCreacion; // Fecha del comentario
}
