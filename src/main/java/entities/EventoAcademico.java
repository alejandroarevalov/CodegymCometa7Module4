package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "eventos_academicos")
@Getter
public class EventoAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Setter
    private String nombre;

    @Column(name = "fecha_evento")
    @Setter
    private LocalDate fechaEvento;

    @Column(name = "hora_evento")
    @Setter
    private LocalTime horaEvento;

    @Column(name = "timestamp_evento")
    @Setter
    private LocalDateTime timestampEvento;

    @Column(name = "timestamp_zonificado")
    private Instant timestampZonificado;

    @Override
    public String toString() {
        DateTimeFormatter fechaHoraFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        ZonedDateTime zonedTimestamp = timestampZonificado != null
            ? timestampZonificado.atZone(ZoneId.of("America/Bogota"))
            : null;

        return "EventoAcademico{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", fechaEvento=" + fechaEvento +
            ", horaEvento=" + horaEvento +
            ", timestampEvento=" + timestampEvento +
            ", timestampZonificado=" + (zonedTimestamp != null ? zonedTimestamp.format(fechaHoraFormatter) : null) +
            '}';
    }

}
