package entities.mappedsuperclass;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
// Prefijo MS = MappedSuperclass
public abstract class MSVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "marca", length = 50)
    protected String marca;
    @Column(name = "modelo", length = 100)
    protected String modelo;
    @Column(name = "anio")
    protected Integer anio;
}