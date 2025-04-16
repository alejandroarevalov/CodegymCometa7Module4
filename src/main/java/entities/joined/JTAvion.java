package entities.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "jt_aviones")
@PrimaryKeyJoinColumn(name = "vehiculo_id")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JTAvion extends JTVehiculo {

    @Column(name = "capacidad_pasajeros")
    private int capacidadPasajeros;

    @Column(name = "alcance_kilometros")
    private double alcanceKilometros;

    @Column(name = "tipo_motor")
    private String tipoMotor;
}