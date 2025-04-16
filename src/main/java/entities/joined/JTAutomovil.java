package entities.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "jt_automoviles")
@PrimaryKeyJoinColumn(name = "vehiculo_id")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JTAutomovil extends JTVehiculo {

    private int puertas;

    @Column(name = "tiene_airbag")
    private boolean tieneAirbag;

    @Column(name = "tipo_combustible")
    private String tipoCombustible;
}