package lesson17.generic;

import java.util.List;

public interface GenericDAO {
    <T> List<T> buscarTodosConTipo(Class<T> tipo, int offset, int limit);

    <T> T buscarPorIdConTipo(Class<T> tipo, int id);

    <T> Long contarConTipo(Class<T> tipo);

    <T> T guardar(T entidad);

    <T> T actualizar(T entidad);

    <T> void eliminar(T entidad);
}
