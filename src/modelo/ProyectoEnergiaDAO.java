package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet; // Aunque no se usa directamente en esta versión simplificada, se mantiene por el CallableStatement
import java.sql.SQLException;
import java.sql.Types;

public class ProyectoEnergiaDAO {

    public ProyectoEnergiaDAO() {
        // Constructor vacío o puedes inicializar algo si es necesario
    }

    public int ejecutarCRUD(String operacion, ProyectoEnergia proyecto) {
        int resultado = -1;
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = ConexionBD.getInstancia().getConexion();
            if (con == null || con.isClosed()) {
                System.err.println("Error: No hay conexión a la base de datos disponible.");
                return -1;
            }

            stmt = con.prepareCall("{call sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, operacion);

            // Solo para CREATE, los otros parámetros son null
            if (operacion.equals("CREATE")) {
                stmt.setNull(2, Types.INTEGER); // ID es nulo para CREATE
                stmt.setString(3, proyecto.getNombreProyecto());
                stmt.setString(4, proyecto.getTipoFuente());
                stmt.setDouble(5, proyecto.getCapacidadMW());
            } else {
                // Si llegamos aquí con otra operación que no sea CREATE, es un error
                // en este módulo simplificado, o los parámetros serían diferentes.
                stmt.setNull(2, Types.INTEGER);
                stmt.setNull(3, Types.VARCHAR);
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.DOUBLE);
            }
           
            stmt.registerOutParameter(6, Types.INTEGER);

            // Para CREATE, ejecutamos y obtenemos el resultado del OUT parameter
            if ("CREATE".equals(operacion)) {
                stmt.execute();
                resultado = stmt.getInt(6); // Obtener el ID generado
            } else {
                System.err.println("Operación no soportada en este módulo simplificado del DAO.");
                resultado = -1;
            }

        } catch (SQLException e) {
            System.err.println("Error en ejecutarCRUD para CREATE: " + e.getMessage());
            e.printStackTrace();
            resultado = -1; // Indicar error
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar el statement: " + e.getMessage());
            }
        }
        return resultado;
    }
}