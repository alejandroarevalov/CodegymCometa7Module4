package lesson16.dao;

import entities.Estudiante;
import entities.NombreCompleto;
import entities.Pais;
import enums.NivelAcademico;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lesson16.dto.EstudianteLibroDTO;
import lesson16.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

public class EstudianteDAOImpl extends AbstractDAO implements EstudianteDAO {

    @Override
    public List<Estudiante> buscarEstudiantesConNivelAcademico(NivelAcademico nivelAcademico) {
        List<Estudiante> resultados = null;
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Estudiante> query = builder.createQuery(Estudiante.class);
            Root<Estudiante> root = query.from(Estudiante.class);
            query.select(root).where(
                builder.equal(root.get("nivelAcademico"), nivelAcademico)
            );
            resultados = session.createQuery(query).getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al recuperar registros de la clase %s: %s",
                Estudiante.class.getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultados;
    }

    @Override
    public List<Estudiante> buscarEstudiantesConFechaNacimientoAnteriorA(LocalDate fechaNacimiento) {
        List<Estudiante> resultados = null;
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Estudiante> query = builder.createQuery(Estudiante.class);
            Root<Estudiante> root = query.from(Estudiante.class);
            query.select(root).where(
                builder.lessThan(root.get("fechaNacimiento"), fechaNacimiento)
            ).orderBy(builder.asc(root.get("fechaNacimiento")));
            resultados = session.createQuery(query).getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al recuperar lista de %ss por fecha de nacimiento %s: %s",
                Estudiante.class.getSimpleName(), fechaNacimiento.toString(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultados;
    }

    @Override
    public List<Estudiante> buscarEstudiantesConNombreYNivelAcademico(String nombre, NivelAcademico nivelAcademico) {
        List<Estudiante> resultados = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Estudiante> query = builder.createQuery(Estudiante.class);
            Root<Estudiante> root = query.from(Estudiante.class);
            Predicate condicionNombre = builder.equal(root.get("nombreCompleto").get("nombre"), nombre);
            Predicate condicionNivelAcademico = builder.equal(root.get("nivelAcademico"), nivelAcademico);
            query.select(root).where(builder.and(condicionNombre, condicionNivelAcademico));
            resultados = session.createQuery(query).getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al recuperar estudiantes con nombre %s y nivel academico %s: %s",
                nombre, nivelAcademico.toString(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultados;
    }

    @Override
    public Long contarEstudiantesDePais(Pais pais) {
        Long resultado = -1L;
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<Estudiante> root = query.from(Estudiante.class);
            Predicate condicionPais = builder.equal(root.get("pais"), pais);
            query.select(builder.count(root)).where(condicionPais);
            resultado = session.createQuery(query).getSingleResult();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al contar estudiantes de pais %s: %s", pais.getNombre(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultado;
    }

    @Override
    public void desactivarEstudiantesConListaDeIds(List<Long> ids) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Estudiante> update = builder.createCriteriaUpdate(Estudiante.class);
            Root<Estudiante> root = update.from(Estudiante.class);
            Predicate condicionListaDeIds = root.get("id").in(ids);
            update.set("activo", Boolean.FALSE).where(condicionListaDeIds);
            // session.createQuery(update).executeUpdate(); --> Deprecado desde Hibernate 6.0
            session.createMutationQuery(update).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al desactivar estudiantes en la lista %s: %s", ids.toString(), e.getMessage());
            System.err.println(mensajeError);
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesConListaDeIds(List<Long> ids) {
        List<Estudiante> resultados = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Estudiante> query = builder.createQuery(Estudiante.class);
            Root<Estudiante> root = query.from(Estudiante.class);
            Predicate condicionListaDeIds = root.get("id").in(ids);
            query.select(root).where(condicionListaDeIds);
            resultados = session.createQuery(query).getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al recuperar estudiantes con ids %s: %s", ids.toString(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultados;
    }

    @Override
    public void eliminarEstudiantePorNombre(NombreCompleto nombreCompleto) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Estudiante> delete = builder.createCriteriaDelete(Estudiante.class);
            Root<Estudiante> root = delete.from(Estudiante.class);
            Predicate condicionNombre = builder.equal(root.get("nombreCompleto").get("nombre"), nombreCompleto.getNombre());
            Predicate condicionApellido = builder.equal(root.get("nombreCompleto").get("apellido"), nombreCompleto.getApellido());
            delete.where(builder.and(condicionNombre, condicionApellido));
            session.createMutationQuery(delete).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al eliminar estudiantes con nombre %s: %s", nombreCompleto.toString(), e.getMessage());
            System.err.println(mensajeError);
        }
    }

    @Override
    public List<EstudianteLibroDTO> cargarDTOEstudiantesLibros() {
        List<EstudianteLibroDTO> estudiantesConLibros = null;
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<EstudianteLibroDTO> query = session.createNativeQuery("""
                SELECT CONCAT(e.nombre,' ', e.apellido) AS nombreEstudiante,
                       l.titulo AS tituloLibro
                  FROM estudiantes e
                  JOIN libros l ON l.estudiante_id = e.id
                 WHERE l.estado = 'VENCIDO'
                 ORDER BY nombreEstudiante
            """, EstudianteLibroDTO.class);
            estudiantesConLibros = query.getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("No fue posible recuperar resultados para la consulta: %s", e.getMessage());
            System.err.println(mensajeError);
        }
        return estudiantesConLibros;
    }
}
