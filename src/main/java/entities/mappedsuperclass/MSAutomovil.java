package entities.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ms_automoviles")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MSAutomovil extends MSVehiculo {
    // No es necesaria la anotación @Column porque el atributo de la clase tiene el mismo nombre que la columna en la tabla
    private Integer puertas;
    @Column(name = "tiene_airbag")
    private Boolean tieneAirbag;
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
}
