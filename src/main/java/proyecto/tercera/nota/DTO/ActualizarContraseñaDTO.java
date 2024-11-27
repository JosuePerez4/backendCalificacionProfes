package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ActualizarContraseñaDTO {

	private String token;
	private String contraseña;
}
