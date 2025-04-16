package entities.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ms_motocicletas")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MSMotocicleta extends MSVehiculo {
    // No es necesaria la anotación @Column porque el atributo de la clase tiene el mismo nombre que la columna en la tabla
    private Integer cilindraje;
    @Column(name = "tipo_suspension")
    private String tipoSuspension;
    @Column(name = "tiene_maletero")
    private Boolean tieneMaletero;
}
