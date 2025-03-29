package lesson11.dao;

import entities.Estudiante;

import java.util.List;

public interface EstudianteDAO {

    List<Estudiante> buscarTodos();

    Estudiante buscarEstudiantePorId(int id);

    void guardar(Estudiante estudiante);

    void actualizar(Estudiante estudiante);

    void eliminar(Estudiante estudiante);

    void eliminarConHQL(int id);

    void eliminarConNativeQuery(int id);

    void eliminarSuave(Estudiante estudiante);

    void sincronizar(Estudiante estudiante);
}
