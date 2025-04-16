package entities.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("AVION")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class STAvion extends STVehiculo {
    @Column(name = "tipo_motor")
    private String tipoMotor;
    @Column(name = "capacidad_pasajeros")
    private Integer capacidadPasajeros;
    @Column(name = "alcance_kilometros")
    private Double alcanceKilometros;
}
