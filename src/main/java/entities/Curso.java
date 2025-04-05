package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@ToString
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Setter
    @Column(name = "numero_creditos", nullable = false)
    private Integer numeroCreditos;

    @Setter
    @Column(name = "descripcion")
    private String descripcion;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "estudiantes_cursos",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id"))
    @ToString.Exclude
    private List<Estudiante> estudiantes = new LinkedList<>();

}
