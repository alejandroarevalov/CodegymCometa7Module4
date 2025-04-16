package entities.joined;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Table(name = "jt_vehiculos")
@Inheritance(strategy = InheritanceType.JOINED)
// @Polymorphism(type = PolymorphismType.EXPLICIT) --> Deprecada desde Hibernate 6.2
@Data
public abstract class JTVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String marca;
    private String modelo;
    private Integer anio;
}
