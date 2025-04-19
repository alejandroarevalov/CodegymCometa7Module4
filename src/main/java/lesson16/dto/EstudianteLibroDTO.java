package lesson16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteLibroDTO {
    private String nombreEstudiante;
    private String tituloLibro;
}