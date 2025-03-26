package lesson10.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "paises")
@Getter
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Setter
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    @Setter
    @OneToMany(mappedBy = "pais")
    private List<Estudiante> estudiantes = new LinkedList<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pais{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", fechaActualizacion=").append(fechaActualizacion);
        sb.append(", estudiantes=").append(estudiantes);
        sb.append('}');
        return sb.toString();
    }
}
