package lesson12.dao;

import entities.EventoAcademico;

import java.util.List;

public interface EventoAcademicoDAO {

    List<EventoAcademico> buscarTodos(int offset, int limit);

    EventoAcademico buscarPorId(int id);

    EventoAcademico agregar(EventoAcademico estudiante);

    EventoAcademico actualizar(EventoAcademico eventoAcademico);

    void eliminar(EventoAcademico eventoAcademico);

}
