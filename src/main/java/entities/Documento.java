package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documentos")
@Getter
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Setter
    private String titulo;

    @Lob
    @Column(name = "contenido_largo", columnDefinition = "LONGTEXT")
    @Setter
    private String contenidoLargo; // CLOB

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    @Setter
    private Byte[] imagen; // BLOB
}