package entities.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("MOTOCICLETA")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class STMotocicleta extends STVehiculo {
    private Integer cilindraje;
    @Column(name = "tipo_suspension")
    private String tipoSuspension;
    @Column(name = "tiene_maletero")
    private Boolean tieneMaletero;
}
