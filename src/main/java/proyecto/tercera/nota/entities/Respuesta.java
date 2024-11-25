package proyecto.tercera.nota.entities;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "respuesta")
public class Respuesta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "encuesta_id")
    private Encuesta encuesta;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    // Respuestas predefinidas, como un Map de tipo pregunta-respuesta
    @ElementCollection
    @MapKeyColumn(name = "pregunta")
    @Column(name = "calificacion")
    @CollectionTable(name = "respuestas_calificaciones", joinColumns = @JoinColumn(name = "respuesta_id"))
    private Map<String, Integer> respuestas = new HashMap<>();

    // Métodos getters y setters
    public Map<String, Integer> getRespuestas() {
        return respuestas;
    }

    public void setRespuesta(String pregunta, int calificacion) {
        respuestas.put(pregunta, calificacion);
    }
}
