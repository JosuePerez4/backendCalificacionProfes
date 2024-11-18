package proyecto.tercera.nota.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="pregunta")
public class Pregunta {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Encuesta encuesta;
    @Column(length = 300)
    private String texto;
    @Column(nullable = false)
    private String tipo;

    @OneToMany(mappedBy = "pregunta")
    private List<Respuesta> respuestas;
}

