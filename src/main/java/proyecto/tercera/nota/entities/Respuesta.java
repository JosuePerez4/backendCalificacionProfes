package proyecto.tercera.nota.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "respuesta")
public class Respuesta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
