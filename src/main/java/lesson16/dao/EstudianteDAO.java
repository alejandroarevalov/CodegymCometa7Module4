package lesson16.dao;

import entities.Estudiante;
import entities.NombreCompleto;
import entities.Pais;
import enums.NivelAcademico;
import lesson16.dto.EstudianteLibroDTO;
import lesson16.generic.GenericDAO;

import java.time.LocalDate;
import java.util.List;

public interface EstudianteDAO extends GenericDAO {

    List<Estudiante> buscarEstudiantesConNivelAcademico(NivelAcademico nivelAcademico);

    List<Estudiante> buscarEstudiantesConFechaNacimientoAnteriorA(LocalDate fechaNacimiento);

    List<Estudiante> buscarEstudiantesConNombreYNivelAcademico(String nombre, NivelAcademico nivelAcademico);

    Long contarEstudiantesDePais(Pais pais);

    void desactivarEstudiantesConListaDeIds(List<Long> ids);

    List<Estudiante> buscarEstudiantesConListaDeIds(List<Long> ids);

    void eliminarEstudiantePorNombre(NombreCompleto nombreCompleto);

    List<EstudianteLibroDTO> cargarDTOEstudiantesLibros();
}