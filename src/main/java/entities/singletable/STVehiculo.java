package entities.singletable;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

@Entity
@Table(name = "st_vehiculos") // nombre personalizado para la tabla
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_vehiculo", discriminatorType = DiscriminatorType.STRING)
/*
@DiscriminatorFormula(
    value = "CASE WHEN puertas IS NOT NULL THEN 'AUTOMOVIL'" +
        " WHEN cilindraje IS NOT NULL THEN 'MOTOCICLETA' " +
        " WHEN tipo_motor IS NOT NULL AND capacidad_pasajeros < 50 THEN 'AVION' END",
    discriminatorType = DiscriminatorType.STRING
)
*/
@Data
// Prefijo ST = SINGLE_TABLE
public abstract class STVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String marca;
    protected String modelo;
    protected int anio;
}
