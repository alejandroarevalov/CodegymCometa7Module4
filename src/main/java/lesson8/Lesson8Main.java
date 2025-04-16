package lesson8;

public class Lesson8Main {

    public static void main(String[] args) {
        EjemplosJDBCEnPractica ejemplos = new EjemplosJDBCEnPractica();
        //ejemplos.ejecutarTransaccion();
        // ejemplos.volverAPuntoDeGuardado();
        //ejemplos.ejecutarConsultaConPreparedStatement("Alejandro");
        /*
        ejemplos.ejecutarInsercionConPreparedStatement(
            "Andres",
            "Forero",
            LocalDate.of(1990, 1, 1)
        );
        */
        // ejemplos.invocarProcedimientoAlmacenadoConCallableStatement(25);
        // ejemplos.ejemploSolicitudPorLotes();
        // ejemplos.ejemploGuardarBlob();
        // ejemplos.ejemploRecuperarBlob();
        // ejemplos.ejemploActualizarRegistroConResultSet();
        ejemplos.ejemploConJdbcRowSet();

        ejemplos.cerrarConexion();
    }
}
