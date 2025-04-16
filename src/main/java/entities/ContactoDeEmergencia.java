package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contactos_de_emergencia")
@Getter
@ToString
public class ContactoDeEmergencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Setter
    @Column(name = "numero_contacto", nullable = false, length = 20)
    private String numeroContacto;
}
