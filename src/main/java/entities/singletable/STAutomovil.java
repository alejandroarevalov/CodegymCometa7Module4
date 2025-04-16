package entities.singletable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("AUTOMOVIL")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class STAutomovil extends STVehiculo {
    private Integer puertas;
    @Column(name = "tiene_airbag")
    private Boolean tieneAirbag;
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
}