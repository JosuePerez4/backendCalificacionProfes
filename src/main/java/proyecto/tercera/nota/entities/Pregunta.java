package proyecto.tercera.nota.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pregunta")
public class Pregunta {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String texto;

	@ManyToOne
	@JoinColumn(name = "encuesta_id")
	private Encuesta encuesta;

	// Si decides no usar entidad Opcion:
	private Integer opciones; // Número de opciones (1-5).

}
