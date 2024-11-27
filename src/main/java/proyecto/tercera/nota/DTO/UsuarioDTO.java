package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioDTO {

	private int id;
	private String correo;
	private int rol;
}
