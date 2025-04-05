package lesson13.dao;

import entities.Curso;

import java.util.List;

public class CursoDAO extends AbstractDAO implements GenericDAO<Curso> {
    @Override
    public List<Curso> buscarTodos(int offset, int limit) {
        return List.of();
    }

    @Override
    public Curso buscarPorId(int id) {
        return null;
    }

    @Override
    public Curso guardar(Curso entidad) {
        return null;
    }

    @Override
    public Curso actualizar(Curso entidad) {
        return null;
    }

    @Override
    public void eliminar(Curso entidad) {

    }
}
