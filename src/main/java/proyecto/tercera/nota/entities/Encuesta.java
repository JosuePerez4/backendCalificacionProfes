package proyecto.tercera.nota.entities;

import java.time.LocalDate;
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
@Table(name="encuesta")
public class Encuesta {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Curso curso;

    @ManyToOne
    private Profesor profesor;

    private LocalDate fechaEnvio;

    @OneToMany(mappedBy = "encuesta")
    private List<Pregunta> preguntas;
}

