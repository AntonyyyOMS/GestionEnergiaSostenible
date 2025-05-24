package modelo;

import java.util.HashMap;
import java.util.Map;

public class ProyectoEnergiaFactory {
    private static Map<String, ProyectoEnergia> tiposProyectos = new HashMap<>();

    static {
        tiposProyectos.put("Solar", new ProyectoEnergia("Solar"));
        tiposProyectos.put("Eolico", new ProyectoEnergia("Eolico"));
        tiposProyectos.put("Hidroelectrico", new ProyectoHidroelectrico("Hidroelectrico"));
        tiposProyectos.put("Biomasa", new ProyectoEnergia("Biomasa"));
    }

    public static ProyectoEnergia crearProyecto(String tipoFuente) {
        ProyectoEnergia tipoProyecto = tiposProyectos.get(tipoFuente);
        if (tipoProyecto != null) {
            try {
                return (ProyectoEnergia) tipoProyecto.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Error al clonar el proyecto: " + e.getMessage()); // Mejor manejo de la excepción
            }
        } else {
            throw new IllegalArgumentException("Tipo de fuente de energía no válido: " + tipoFuente);
        }
    }
}
