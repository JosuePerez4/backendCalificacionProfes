package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfesorDTO {
	private String nombre;
	private String correo;
}
