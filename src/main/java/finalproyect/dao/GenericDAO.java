package finalproyect.dao;

import java.util.List;

public interface GenericDAO {

    <T> T buscarPorId(Class<T> tipo, Integer id);

    <T> List<T> buscarTodos(Class<T> tipo);

    <T> T guardar(T entidad);

    <T> T actualizar(T entidad);

    <T> void eliminar(T entidad);
}
