package proyecto.tercera.nota.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {
	private String nombre;
	private String codigo;
	private String nombreProfesor;
}
