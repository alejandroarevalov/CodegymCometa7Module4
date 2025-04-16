package entities.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "jt_motocicletas")
@PrimaryKeyJoinColumn(name = "vehiculo_id")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JTMotocicleta extends JTVehiculo {

    private int cilindraje;

    @Column(name = "tipo_suspension")
    private String tipoSuspension;

    @Column(name = "tiene_maletero")
    private boolean tieneMaletero;
}
