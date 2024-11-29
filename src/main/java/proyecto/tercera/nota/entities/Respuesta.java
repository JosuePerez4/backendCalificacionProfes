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
@Table(name = "respuesta")
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "calificacion")
	private Integer calificacion; // La respuesta numérica (1-5).

	@Column(name = "texto_respuesta", length = 500)
	private String textoRespuesta; // Si es una respuesta abierta.

	@ManyToOne
	@JoinColumn(name = "pregunta_id", nullable = false)
	private Pregunta pregunta;

	@ManyToOne
	@JoinColumn(name = "aplicacion_encuesta_id", nullable = false)
	private AplicacionEncuesta aplicacionEncuesta;
}
