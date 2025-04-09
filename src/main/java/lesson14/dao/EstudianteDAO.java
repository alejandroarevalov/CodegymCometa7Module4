package lesson14.dao;

import entities.Estudiante;

public interface EstudianteDAO extends GenericDAO<Estudiante> {
    Estudiante buscarPorIdUsandoFetchJoin(int id);

    Estudiante buscarPorIdMostrandoCacheDePrimerNivel(int id);

    Estudiante buscarPorIdUsando2SesionesConCacheDeSegundoNivel(int id);

    Estudiante buscarPorIdUsando2SesionesConCacheDeConsultas(int id);

    void borrarEstudianteDeCache(int id);
}
