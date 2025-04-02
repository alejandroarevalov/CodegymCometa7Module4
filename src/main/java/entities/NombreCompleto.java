package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data // Getters y setters
@AllArgsConstructor
@NoArgsConstructor
public class NombreCompleto {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;

    @Override
    public String toString() {
        return "NombreCompleto{" + "nombre=" + nombre + ", apellido=" + apellido + '}';
    }
}
