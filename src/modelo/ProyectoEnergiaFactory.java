package modelo;

import java.util.HashMap; // [cite: 2]
import java.util.Map; // [cite: 2]

public class ProyectoEnergiaFactory { // [cite: 2]
    private static Map<String, ProyectoEnergia> tiposProyectos = new HashMap<>(); // [cite: 2]

    static { // [cite: 2]
        tiposProyectos.put("Solar", new ProyectoEnergia("Solar")); // [cite: 2]
        tiposProyectos.put("Eólica", new ProyectoEnergia("Eólica")); // Asegúrate de que el String coincida con el JComboBox
        tiposProyectos.put("Hidroeléctrica", new ProyectoHidroelectrico("Hidroeléctrica")); // [cite: 2] // Asegúrate de que el String coincida
        tiposProyectos.put("Geotérmica", new ProyectoEnergia("Geotérmica")); // Si lo usas en el ComboBox
        tiposProyectos.put("Biomasa", new ProyectoEnergia("Biomasa")); // [cite: 2]
    }

    public static ProyectoEnergia crearProyecto(String tipoFuente) { // [cite: 2]
        ProyectoEnergia tipoProyecto = tiposProyectos.get(tipoFuente); // [cite: 2]
        if (tipoProyecto != null) { // [cite: 2]
            try {
                return (ProyectoEnergia) tipoProyecto.clone(); // [cite: 2]
            } catch (CloneNotSupportedException e) { // [cite: 2]
                throw new RuntimeException("Error al clonar el proyecto: " + e.getMessage()); // [cite: 2]
            }
        } else {
            throw new IllegalArgumentException("Tipo de fuente de energía no válido: " + tipoFuente); // [cite: 2]
        }
    }
}