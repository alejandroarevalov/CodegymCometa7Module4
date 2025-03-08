package lesson7;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class EjemplosJDBC {

    private Connection conexion;

    public Connection crearConexion() throws SQLException {
        if (conexion == null) {
            conexion  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cometa7",
                "root", "root");
        }
        return conexion;
    }

    public void ejecutarConsulta() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");

        System.out.println("Fila | Id | Nombre | Apellido");

        while (resultados.next()) {
            int id = resultados.getInt(1);
            String nombre = resultados.getString(2);
            String apellido = resultados.getString(3);
            System.out.println(resultados.getRow() + " | " + id + " | "+ nombre + " | "+ apellido);
        }
    }

    public void obtenerDatosDelResultSet() throws SQLException {
        Connection conexion = crearConexion();
        // ResultSet.TYPE_FORWARD_ONLY
        // ResultSet.TYPE_SCROLL_INSENSITIVE
        // ResultSet.TYPE_SCROLL_SENSITIVE
        Statement declaracion = conexion.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");

        System.out.println("Iniciando lectura");

        System.out.println("Fila actual: " + resultados.getRow()); // Linea 0 (no se han empezado a leer registros)
        System.out.println("Antes del primero?: " + resultados.isBeforeFirst()); // true
        System.out.println("Es el primero?: " + resultados.isFirst()); // false

        System.out.println("Leyendo el primer registro");
        resultados.next();

        System.out.println("Fila actual: " + resultados.getRow()); // Linea 1 (Ha pasado el primer registro)
        System.out.println("Antes del primero?: " + resultados.isBeforeFirst()); // false
        System.out.println("Es el primero?: " + resultados.isFirst()); // false

        System.out.println("Leyendo el segundo registro");
        resultados.next();

        System.out.println("Fila actual: " + resultados.getRow()); // Linea 2 (Ha pasado al segundo registro)
        System.out.println("Antes del primero?: " + resultados.isBeforeFirst()); // false
        System.out.println("Es el primero?: " + resultados.isFirst()); // false

        resultados.last(); // ir al ultimo registro recuperado
        System.out.println("Cantidad de resultados de la consulta: " + resultados.getRow()
                + " resultados recuperados");
    }

    public void obtenerDatosFilaActual() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");
        resultados.next();
        System.out.println(
            resultados.getInt(1) + " | " +
            resultados.getString(2) + " | " +
            resultados.getString(3) + " | " +
            resultados.getDate(4) + " | " +
            resultados.getString(5) + " | " +
            resultados.getInt(6)
        );
        resultados.next();
        System.out.println(
            resultados.getInt("id") + " | " +
            resultados.getString("nombre") + " | " +
            resultados.getString("apellido") + " | " +
            resultados.getDate("fecha_nacimiento") + " | " +
            resultados.getString("numero_contacto") + " | " +
            resultados.getInt("pais_id")
        );
    }

    public void obtenerMetadatosDelResultSet() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");
        ResultSetMetaData metadatos = resultados.getMetaData();
        int numeroColumnas = metadatos.getColumnCount();
        System.out.println("Número de columnas resultados: " + numeroColumnas);

        for (int i = 1; i <= numeroColumnas; i++) {
            System.out.println("Columna # " + i);
            System.out.println("Nombre de columna: " + metadatos.getColumnName(i));
            System.out.println("Etiqueta de columna: " + metadatos.getColumnLabel(i));
            System.out.println("Tipo de dato (código SQL): " + metadatos.getColumnType(i));
            System.out.println("Tipo de dato (nombre): " + metadatos.getColumnTypeName(i));
            System.out.println("Clase de dato en Java: " + metadatos.getColumnClassName(i));
            System.out.println("Nombre de la tabla: " + metadatos.getTableName(i));
            System.out.println("Nombre del catálogo: " + metadatos.getCatalogName(i));
            System.out.println("Nombre del esquema: " + metadatos.getSchemaName(i));
            System.out.println("Es autoincremental: " + metadatos.isAutoIncrement(i));
            System.out.println("Permite nulos: " + (metadatos.isNullable(i) == ResultSetMetaData.columnNullable ? "Sí" : "No"));
            System.out.println();
        }
    }

    public void manejoDeNulls() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");

        resultados.next();
        System.out.println(
            resultados.getInt("id") + " | " +
            resultados.getString("nombre") + " | " +
            resultados.getString("apellido") + " | " +
            resultados.getDate("fecha_nacimiento") + " | " +
            resultados.getString("numero_contacto") + " | " +
            resultados.getInt("pais_id")
        );
        int paisId = resultados.getInt("pais_id");
        if (resultados.wasNull()) {
            System.out.println("El pais realmente es nulo en la base de datos");
        } else {
            System.out.println("El pais realmente es: " + paisId);
        }
        resultados.next();
        resultados.next();
        resultados.next();
        resultados.next();
        System.out.println(
            resultados.getInt("id") + " | " +
            resultados.getString("nombre") + " | " +
            resultados.getString("apellido") + " | " +
            resultados.getDate("fecha_nacimiento") + " | " +
            resultados.getString("numero_contacto") + " | " +
            resultados.getInt("pais_id")
        );
        paisId = resultados.getInt("pais_id");
        if (resultados.wasNull()) {
            System.out.println("El pais realmente es nulo en la base de datos");
        } else {
            System.out.println("El pais realmente es: " + paisId);
        }

    }

    public void manejoDeFechas() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        ResultSet resultados = declaracion.executeQuery("SELECT * FROM estudiantes");

        resultados.next();
        System.out.println(
            resultados.getInt("id") + " | " +
            resultados.getString("nombre") + " | " +
            resultados.getString("apellido") + " | " +
            resultados.getDate("fecha_nacimiento") + " | " +
            resultados.getString("numero_contacto") + " | " +
            resultados.getInt("pais_id")
        );
        Date fechaNacimiento = resultados.getDate("fecha_nacimiento");
        LocalDate fechaNacimientoLocalDate = fechaNacimiento.toLocalDate();
        LocalDate fechaNacimientoFormaAlternativa = resultados.getObject(4, LocalDate.class);
        System.out.println("Fecha de nacimiento: " + fechaNacimientoLocalDate);
        System.out.println("Fecha de nacimiento alternativa: " + fechaNacimientoFormaAlternativa);
        Instant instant = fechaNacimientoLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        System.out.println("Fecha de nacimiento: " + instant);

    }

    public void usarMetodoExecuteDeResultSet() throws SQLException {
        Connection conexion = crearConexion();
        Statement declaracion = conexion.createStatement();
        boolean booleano = declaracion.execute("DELETE FROM estudiantes WHERE id = 206");
        if (booleano) {
            ResultSet resultados = declaracion.getResultSet();
            resultados.next();
            System.out.println(
                resultados.getInt("id") + " | " +
                resultados.getString("nombre") + " | " +
                resultados.getString("apellido") + " | " +
                resultados.getDate("fecha_nacimiento") + " | " +
                resultados.getString("numero_contacto") + " | " +
                resultados.getInt("pais_id")
            );
        } else {
            System.out.println("Cantidad de registros afectados: " + declaracion.getUpdateCount());
        }

        // Revisar cual es la llave primaria de una tabla
        boolean resultado = declaracion.execute("describe estudiantes");
        if (resultado) {
            ResultSet resultados = declaracion.getResultSet();
            while (resultados.next()) {
                String llavePrimaria = resultados.getString("Key");
                if ("PRI".equals(llavePrimaria)) {
                    System.out.println("La llave primaria de la tabla estudiantes es: " +
                        resultados.getString("field"));
                    break;
                }
            }
        }
    }

    public void cerrarConexion() throws SQLException {
        conexion.close();
    }
}
