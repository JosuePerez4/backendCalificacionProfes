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
@Table(name="curso")
public class Curso {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre", length = 100)
    private String nombre;
    @Column(name="codigo", length = 10)
    private String codigo;
    @Column(name="periodo", length = 10)
    private String periodo;

    @ManyToOne
    private Profesor profesor;  // Relación con Profesor

    @OneToMany(mappedBy = "curso")
    private List<Encuesta> encuestas;
}

