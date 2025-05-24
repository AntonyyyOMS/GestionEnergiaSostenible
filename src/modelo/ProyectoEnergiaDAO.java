package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class ProyectoEnergiaDAO {

    private static Map<String, ProyectoEnergia> prototipos = new HashMap<>();

    static {
        prototipos.put("Solar", new ProyectoEnergia("Solar"));
        prototipos.put("Eolico", new ProyectoEnergia("Eolico"));
        prototipos.put("Hidroelectrico", new ProyectoHidroelectrico("Hidroelectrico"));
        prototipos.put("Biomasa", new ProyectoEnergia("Biomasa"));
    }

    public ProyectoEnergia crearProyectoEnergia(String tipoFuente) {
        // Usamos la factoría para obtener el prototipo
        return ProyectoEnergiaFactory.crearProyecto(tipoFuente);
    }

    public int ejecutarCRUD(String operacion, ProyectoEnergia proyecto) {
        int resultado = -1;
        Connection con = null;
        CallableStatement stmt = null;

        try {
            con = ConexionBD.getInstancia().getConexion();
            stmt = con.prepareCall("{call sp_crud_proyectoEnergia(?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, operacion);
            stmt.setObject(2, "CREATE".equals(operacion) ? null : proyecto.getId());
            stmt.setString(3, proyecto.getNombreProyecto());
            stmt.setString(4, proyecto.getTipoFuente());
            stmt.setDouble(5, proyecto.getCapacidadMW());
            stmt.registerOutParameter(6, Types.INTEGER);

            boolean tieneResultado = stmt.execute();

            if ("READ".equals(operacion) && tieneResultado) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    ProyectoEnergia p = new ProyectoEnergia();
                    p.setId(rs.getInt("id"));
                    p.setNombreProyecto(rs.getString("nombreProyecto"));
                    p.setTipoFuente(rs.getString("tipoFuente"));
                    p.setCapacidadMW(rs.getDouble("CapacidadMW")); // Asegúrate de que "CapacidadMW" coincida con tu base de datos
                    System.out.println(p);
                }
            } else if ("CREATE".equals(operacion)) {
                resultado = stmt.getInt(6);
            } else {
                resultado = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error en ejecutarCRUD: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                // No cerramos la conexión aquí, ya que es manejada por el Singleton
            } catch (SQLException e) {
                System.err.println("Error al cerrar el statement: " + e.getMessage());
            }
        }
        return resultado;
    }
}
