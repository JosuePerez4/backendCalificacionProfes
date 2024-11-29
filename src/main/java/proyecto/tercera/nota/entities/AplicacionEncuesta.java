package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class AplicacionEncuesta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String textoPositivo;
	private String textoNegativo;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "profesor_id")
	private Profesor profesor;

	@ManyToOne
	@JoinColumn(name = "encuesta_id")
	private Encuesta encuesta;

	@ElementCollection
	private List<Integer> respuestas; // Calificaciones (1-5) para cada pregunta.

	@ManyToOne
	@JoinColumn(name = "materia_id", nullable = false)
	private Materia materia;
}
