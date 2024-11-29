package proyecto.tercera.nota.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AplicacionEncuestaDTO {

	private String codigoMateria;
	private String profesor;
	private String estudiante;
	private List<Integer> respuestas; // Calificaciones de 1 a 5
	private String textoPositivo;
	private String textoNegativo;

}
