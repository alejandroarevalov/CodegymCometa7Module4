package lesson9.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "Estudiantes", schema = "cometa7")
public class Estudiante {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Getter @Setter
    @Column
    private String apellido;

    @Getter @Setter
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Getter @Setter
    @ColumnDefault("'N/A'")
    @Column(name = "numero_contacto", length = 20)
    private String numeroContacto;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estudiante{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", apellido='").append(apellido).append('\'');
        sb.append(", fechaNacimiento=").append(fechaNacimiento);
        sb.append(", numeroContacto='").append(numeroContacto).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
