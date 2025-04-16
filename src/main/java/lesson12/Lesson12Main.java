package lesson12;

import entities.Documento;
import entities.Estudiante;
import entities.EventoAcademico;
import entities.NombreCompleto;
import enums.NivelAcademico;
import lesson12.dao.DocumentoDAOImpl;
import lesson12.dao.EstudianteDAOImpl;
import lesson12.dao.EventoAcademicoDAOImpl;
import lesson12.dao.GenericDAO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Lesson12Main {

    private final GenericDAO<Estudiante> estudianteDAO;
    private final GenericDAO<EventoAcademico> eventoAcademicoDAO;
    private final GenericDAO<Documento> documentoDAO;

    public Lesson12Main(GenericDAO<Estudiante> estudianteDAO,
                        GenericDAO<EventoAcademico> eventoAcademicoDAO,
                        GenericDAO<Documento> documentoDAO) {
        this.estudianteDAO = estudianteDAO;
        this.eventoAcademicoDAO = eventoAcademicoDAO;
        this.documentoDAO = documentoDAO;
    }

    public void buscarTodosLosEstudiantes() {
        int offset = 0;
        int limit = 10;
        List<Estudiante> estudiantes = estudianteDAO.buscarTodos(offset, limit);
        estudiantes.forEach(System.out::println);
    }

    public void buscarEstudiantePorId(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorId(1);
        System.out.println("Estudiante encontrado: " + estudiante);
    }

    public void insertarEstudiante() {
        Estudiante estudiante = Estudiante.builder()
            .conNombreCompleto(new NombreCompleto("Daniel", "Buitrago"))
            .conFechaNacimiento(LocalDate.of(2010, 4, 18))
            .conNumeroContacto("+5722222224")
            .conMayorDeEdad(true)
            .conActivo(true)
            .conNivelAcademico(NivelAcademico.SECUNDARIA)
            .build();
        estudiante = estudianteDAO.guardar(estudiante);
        System.out.println("Estudiante agregado: " + estudiante);
    }

    public void actualizarEstudiante() {
        Estudiante estudiante = estudianteDAO.buscarPorId(384);
        estudiante.setActivo(!estudiante.getActivo());
        estudiante = estudianteDAO.actualizar(estudiante);
        System.out.println("Estudiante actualizado: " + estudiante);
    }

    public void buscarTodosLosEventosAcademicos() {
        List<EventoAcademico> eventoAcademicos = eventoAcademicoDAO.buscarTodos(0, 0);
        eventoAcademicos.forEach(System.out::println);
    }

    public void insertarDocumento() {
        Documento documento = new Documento();
        documento.setTitulo("Documento 1");
        Path rutaTexto = Paths.get("src/main/resources/texto_largo.txt");
        Path rutaImagen = Paths.get("src/main/resources/Tipos de GenerationType.png");
        try {
            documento.setContenidoLargo(Files.readString(rutaTexto, StandardCharsets.UTF_8));
            byte[] fileBytes = Files.readAllBytes(rutaImagen);
            Byte[] fileByteArray = new Byte[fileBytes.length];
            for (int i = 0; i < fileBytes.length; i++) {
                fileByteArray[i] = fileBytes[i];
            }
            documento.setImagen(fileByteArray);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        documentoDAO.guardar(documento);
        System.out.println("Documento guardado exitosamente: " + documento);
    }

    public void buscarDocumentoPorId(int id) {
        Documento documento = documentoDAO.buscarPorId(id);
        if (documento != null) {
            try {
                System.out.println("Documento encontrado: " + documento.getTitulo());
                Path rutaSalidaTexto = Paths.get("src/main/resources/texto_recuperado.txt");
                Path rutaSalidaImagen = Paths.get("src/main/resources/imagen_recuperada.png");
                Files.writeString(rutaSalidaTexto, documento.getContenidoLargo(), StandardCharsets.UTF_8);
                System.out.println("Texto guardado en: " + rutaSalidaTexto.getFileName());
                byte[] fileBytes = new byte[documento.getImagen().length];
                for (int i = 0; i < fileBytes.length; i++) {
                    fileBytes[i] = documento.getImagen()[i];
                }
                Files.write(rutaSalidaImagen, fileBytes);
                System.out.println("Imagen exportada a: " + rutaSalidaImagen.getFileName());
            } catch (IOException | RuntimeException e) {
                System.err.println("Error al leer el documento: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        GenericDAO<Estudiante> estudianteDAO = new EstudianteDAOImpl();
        GenericDAO<EventoAcademico> eventoAcademicoDAO = new EventoAcademicoDAOImpl();
        GenericDAO<Documento> documentoDAO = new DocumentoDAOImpl();
        Lesson12Main main = new Lesson12Main(estudianteDAO, eventoAcademicoDAO, documentoDAO);
        String dash = "-";
        int dashRepetitions = 140;
        main.buscarTodosLosEstudiantes();
        System.out.println(dash.repeat(dashRepetitions));
        main.buscarEstudiantePorId(1);
        System.out.println(dash.repeat(dashRepetitions));
        // main.insertarEstudiante();
        main.actualizarEstudiante();
        System.out.println(dash.repeat(dashRepetitions));
        main.buscarTodosLosEventosAcademicos();
        System.out.println(dash.repeat(dashRepetitions));
        main.insertarDocumento();
        main.buscarDocumentoPorId(1);
    }
}
