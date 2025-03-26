package lesson10.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "Estudiantes", schema = "cometa7")
@Getter
@NamedQueries({
    @NamedQuery(name = "Estudiante.obtenerTodos", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.porPais", query = "SELECT e FROM Estudiante e WHERE e.pais.nombre = :nombrePais"),
    @NamedQuery(name = "Estudiante.conNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre LIKE :patronNombre")
})
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Setter
    @Column
    private String apellido;

    @Setter
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Setter
    @ColumnDefault("'N/A'")
    @Column(name = "numero_contacto", length = 20)
    private String numeroContacto;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id", nullable = true)
    private Pais pais;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estudiante{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", apellido='").append(apellido).append('\'');
        sb.append(", fechaNacimiento=").append(fechaNacimiento);
        sb.append(", numeroContacto='").append(numeroContacto).append('\'');
        sb.append(", pais=").append(pais == null ? "No tiene pais" : pais.getNombre());
        sb.append('}');
        return sb.toString();
    }
}
