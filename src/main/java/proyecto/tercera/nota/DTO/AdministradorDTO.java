package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdministradorDTO {

	private int id;
	private String usuario;
	private String nombre;
	private String email;
}
