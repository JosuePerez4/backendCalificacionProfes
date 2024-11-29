package proyecto.tercera.nota.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AplicacionEncuestaDTO {

	private int encuestaId;
	private int profesorId;
	private int usuarioId;
	private List<Integer> respuestas; // Calificaciones de 1 a 5
	private String textoPositivo;
	private String textoNegativo;

}
