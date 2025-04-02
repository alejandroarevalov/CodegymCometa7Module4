package entities;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import lesson12.enums.NivelAcademico;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "Estudiantes", schema = "cometa7")
@NamedQueries({
    @NamedQuery(name = "Estudiante.obtenerTodos", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.porPais", query = "SELECT e FROM Estudiante e WHERE e.pais.nombre = :nombrePais"),
    @NamedQuery(name = "Estudiante.conNombre", query = "SELECT e FROM Estudiante e WHERE e.nombreCompleto.nombre LIKE :patronNombre")
})
@Getter
@Builder(setterPrefix = "con")
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "estudiantesActivos", parameters = @ParamDef(name = "activo", type = Integer.class))
@Filter(name = "estudiantesActivos", condition = "activo = :activo")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ver: src/main/resources/Tipos de GenerationType.png
    private Integer id;

    @Setter
    @Embedded
    private NombreCompleto nombreCompleto;

    @Setter
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Formula("(YEAR(CURDATE()) - YEAR(fecha_nacimiento)) -\n" +
        "(DATE_FORMAT(CURDATE(), '%m%d') < DATE_FORMAT(fecha_nacimiento, '%m%d'))")
    private Integer edad;

    @Transient
    private Boolean mayorDeEdad;

    @Setter
    @ColumnDefault("'N/A'")
    @Column(name = "numero_contacto", length = 20)
    private String numeroContacto;

    @Setter
    @Column(name = "activo", columnDefinition = "BIT(1)")
    @Convert(converter = NumericBooleanConverter.class) // YesNoConverter, TrueFalseConverter
    private Boolean activo;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @Setter
    @Enumerated(EnumType.STRING) //EnumType.Ordinal (no recomendado)
    @Column(name = "nivel_academico")
    private NivelAcademico nivelAcademico;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public Boolean getMayorDeEdad() {
         this.mayorDeEdad = (
             fechaNacimiento != null &&
             Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18
         );
         return this.mayorDeEdad;
    }

    @PrePersist
    public void antesDeInsertar() {
        System.out.println("→ Insertando estudiante...");
    }

    @PostPersist
    public void despuesDeInsertar() {
        System.out.println("✓ Estudiante insertado con éxito");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estudiante{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombreCompleto.getNombre()).append('\'');
        sb.append(", apellido='").append(nombreCompleto.getApellido()).append('\'');
        sb.append(", nombreCompleto='").append(nombreCompleto).append('\'');
        sb.append(", fechaNacimiento=").append(fechaNacimiento);
        sb.append(", edad=").append(edad);
        sb.append(", esMayorDeEdad=").append(getMayorDeEdad());
        sb.append(", numeroContacto='").append(numeroContacto).append('\'');
        sb.append(", pais=").append(pais == null ? "No tiene pais" : pais.getNombre());
        sb.append(", activo=").append(activo);
        sb.append(", nivelAcademico=").append(nivelAcademico);
        sb.append('}');
        return sb.toString();
    }
}
