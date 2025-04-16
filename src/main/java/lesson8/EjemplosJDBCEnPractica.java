package lesson8;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;

public class EjemplosJDBCEnPractica {

    private Connection conexion;

    public EjemplosJDBCEnPractica() {
        crearConexion();
    }

    public void ejecutarTransaccion() {
        try {
            conexion.setAutoCommit(false);
            Statement declaracion = conexion.createStatement();
            declaracion.execute("INSERT INTO paises (nombre, fecha_actualizacion) VALUES ('Un pais lejano', NOW())");
            // statement.getGeneratedKeys().next(); -> Para recuperar el id de un nuevo registro insertado
            declaracion.execute(
            "UPDATE estudiantes e SET e.pais_id = " +
                "(SELECT id FROM paises WHERE nombre = 'Un pais lejano') " +
                "WHERE e.nombre = 'Alejandro' AND e.apellido = 'Arevalo'");
            conexion.commit();
            System.out.println("Transaccion exitosa");
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("No se pudo deshacer la transacción");
            }
            System.err.println("Error al ejecutar la transacción: " + e);
        }
    }

    public void volverAPuntoDeGuardado() {
        try {
            conexion.setAutoCommit(false);
            Statement declaracion = conexion.createStatement();
            int filasAfectadas = declaracion.executeUpdate("UPDATE estudiantes SET pais_id = 104 WHERE id = 8");
            System.out.println("Cantidad de registros afectados: " + filasAfectadas);
            // Accion 2
            // Accion 3
            Savepoint puntoDeGuardado = conexion.setSavepoint();
            // Accion 4
            // Accion 5
            try{
                filasAfectadas = declaracion.executeUpdate("UPDATE estudiantes SET pais_id = -4 WHERE id = 3");
            }
            catch (SQLException e) {
                System.err.println("Error parcial en la transacción: " + e);
                conexion.rollback(puntoDeGuardado);
            }
            conexion.commit();
            conexion.setAutoCommit(true);
        }
        catch (SQLException e) {
            try {
                System.err.println("Error en la totalidad de la transacción: " + e);
                conexion.rollback();
                return;
            } catch (SQLException ex) {
                System.err.println("No se pudo ejecutar la transacción completamente");
            }
        }
        System.out.println("Transacción con punto de guardado exitosa");
    }

    public void ejecutarConsultaConPreparedStatement(String nombreEstudiante) {
        String sql = "SELECT id, nombre, apellido, fecha_nacimiento FROM estudiantes WHERE nombre = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, nombreEstudiante); // Reemplaza con el valor a buscar

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("nombre | apelllido | fechaNacimiento");
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                    System.out.println(nombre + " | " + apellido + " | " + fechaNacimiento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    public void ejecutarInsercionConPreparedStatement(String nombre, String apellido, LocalDate fechaNacimiento) {
        String sql = "INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, nombre); // Nombre del estudiante
            stmt.setString(2, apellido); // Apellido del estudiante
            stmt.setDate(3, Date.valueOf(fechaNacimiento)); // Fecha de nacimiento del estudiante

            int filasInsertadas = stmt.executeUpdate(); // Ejecuta la inserción

            if (filasInsertadas > 0) {
                System.out.println("Registro insertado exitosamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    public void invocarProcedimientoAlmacenadoConCallableStatement(double gradosCelsius) {
        try (CallableStatement stmt = conexion.prepareCall("{CALL ConvertirCelsiusAFahrenheit(?, ?)}")) {

            stmt.setDouble(1, gradosCelsius); // Asigna el valor de entrada
            stmt.registerOutParameter(2, Types.DECIMAL); // Registra el parámetro de salida

            stmt.execute(); // Ejecuta el procedimiento almacenado

            double fahrenheit = stmt.getDouble(2); // Obtiene el resultado
            System.out.println(gradosCelsius + "°C = " + fahrenheit + "°F");

        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    public void ejemploSolicitudPorLotes() {
        String sql = "INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) VALUES (?, ?, ?, ?)";
        try (PreparedStatement declaracionPreparada = conexion.prepareStatement(sql)) {

            for (int i = 0; i < 5; i++) {
                // Fill in the request parameters
                declaracionPreparada.setString(1, "Nombre-" + i);
                declaracionPreparada.setString(2, "Apellido-" + i);
                Date fechaNacimiento = Date.valueOf(
                    LocalDate.of(1991, 3, 11).plusDays(i)
                );
                declaracionPreparada.setDate(3, fechaNacimiento);
                declaracionPreparada.setString(4, "123123123_" + i);
                declaracionPreparada.addBatch();
            }
            // Ejecutar todas las peticiones al instante
            int[] resultados = declaracionPreparada.executeBatch();
            System.out.println("Registros insertados: " + resultados.length);
        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    public void ejemploGuardarBlob() {
        Persona persona = new Persona("Donald Trump", 60);

        try (PreparedStatement declaracionPreparada =
                 conexion.prepareStatement("INSERT INTO objetos (nombre, datos) VALUES (?, ?)")) {

            // Serializar el objeto
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(persona);
            objStream.close();

            // Convertir a arreglo de bytes y guardar en el BLOB
            byte[] bytes = byteStream.toByteArray();
            declaracionPreparada.setString(1, persona.getNombre()); // Nombre
            declaracionPreparada.setBytes(2, bytes); // BLOB

            declaracionPreparada.executeUpdate();
            System.out.println("Objeto guardado exitosamente en la base de datos.");

        } catch (SQLException | IOException e) {
            System.err.println("Error en la transferencia del objeto a la base de datos: " + e);
        }
    }

    public void ejemploRecuperarBlob() {
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT datos FROM objetos WHERE id = ?")) {

            stmt.setInt(1, 2); // ID del objeto a recuperar
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] bytes = rs.getBytes("datos");
                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objStream = new ObjectInputStream(byteStream);

                Persona persona = (Persona) objStream.readObject();
                objStream.close();

                System.out.println("Objeto recuperado: " + persona);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ejemploActualizarRegistroConResultSet() {
        try (Statement stmt = conexion.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, apellido, pais_id FROM estudiantes");

            // Moverse al primer registro
            if (rs.next()) {
                // Modificar el pais del primer estudiante
                rs.updateInt("pais_id", 100);
                rs.updateRow(); // Refleja el cambio en la BD
                System.out.println("País actualizado en la base de datos.");
            }

        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    public void ejemploConJdbcRowSet() {
        String url = "jdbc:mysql://localhost:3306/cometa7";
        String usuario = "root";
        String clave = "root";

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            // Configurar la conexión
            rowSet.setUrl(url);
            rowSet.setUsername(usuario);
            rowSet.setPassword(clave);

            // Ejecutar consulta (JdbcRowSet siempre está conectado)
            rowSet.setCommand("SELECT id, nombre, apellido, fecha_nacimiento, pais_id FROM estudiantes");
            rowSet.execute();

            // Recorrer los datos
            System.out.println("Estudiantes antes de la actualización:");
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("id") +
                    ", Nombre: " + rowSet.getString("nombre") +
                    ", Apellido: " + rowSet.getString("apellido") +
                    ", Fecha nacimiento: " + rowSet.getDate("fecha_nacimiento") +
                    ", Pais ID: " + rowSet.getInt("pais_id")
                );
            }

            // Volver al primer registro
            rowSet.beforeFirst();

            // Actualizar un registro
            if (rowSet.next()) {
                int pais = 50;

                rowSet.updateInt("pais_id", pais);
                rowSet.updateRow(); // Actualiza directamente en la BD
                System.out.println("Pais actualizado para el primer estudiante.");
            }

            // Volver a recorrer para verificar los cambios
            rowSet.beforeFirst();
            System.out.println("\nEstudiantes después de la actualización:");
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("id") +
                    ", Nombre: " + rowSet.getString("nombre") +
                    ", Apellido: " + rowSet.getString("apellido") +
                    ", Fecha nacimiento: " + rowSet.getDate("fecha_nacimiento") +
                    ", Pais ID: " + rowSet.getInt("pais_id")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error en la transacción: " + e);
        }
    }

    private void crearConexion() {
        try {
            if (conexion == null) {
                conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cometa7",
                    "root", "root");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear la conexión a la base de datos: " + e);
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("No se pudo cerrar la conexión a la base de datos: " + e);
        } finally {
            conexion = null;
        }
    }
}
