package entities.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ms_aviones")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MSAvion extends MSVehiculo {
    @Column(name = "tipo_motor")
    private String tipoMotor;
    @Column(name = "capacidad_pasajeros")
    private Integer capacidadPasajeros;
    @Column(name = "alcance_kilometros")
    private Double alcanceKilometros;
}
