package lesson14.dao;

import java.util.List;

public interface GenericDAO<T> {

    List<T> buscarTodos(int offset, int limit);

    T buscarPorId(int id);

    T guardar(T entidad);

    T actualizar(T entidad);

    void eliminar(T entidad);
}
