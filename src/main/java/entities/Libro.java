package entities;

import enums.EstadoLibro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "libros")
@Getter
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 200)
    @Setter
    private String titulo;

    @Column(name = "isbn", length = 20)
    @Setter
    private String isbn;

    @Column(name = "fecha_publicacion")
    @Setter
    private LocalDate fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @Setter
    private EstadoLibro estado;

    @Column(name = "fecha_prestamo")
    @Setter
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion_esperada")
    @Setter
    private LocalDate fechaDevolucionEsperada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @Setter
    private Estudiante estudiante;
}
