package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EstudianteDTO {

	private String codigo;
	private String nombre;
	private String email;
}
