package vista;

import modelo.ProyectoEnergia;
import javax.swing.JTextArea;

public class ProyectoEnergiaVista {

    private JTextArea outputArea;

    // Constructor modificado para recibir el JTextArea
    public ProyectoEnergiaVista(JTextArea outputArea) {
        this.outputArea = outputArea;
    }

    // Constructor sin JTextArea (para usos de consola o si no se necesita la GUI)
    public ProyectoEnergiaVista() {
        this.outputArea = null;
    }

    public void mostrarDetallesProyecto(ProyectoEnergia proyecto) {
        if (outputArea != null) {
            if (proyecto != null) {
                outputArea.append("Detalles del Proyecto:\n");
                outputArea.append("  ID: " + proyecto.getId() + "\n");
                outputArea.append("  Nombre: " + proyecto.getNombreProyecto() + "\n");
                outputArea.append("  Tipo de Fuente: " + proyecto.getTipoFuente() + "\n");
                outputArea.append("  Capacidad (MW): " + proyecto.getCapacidadMW() + "\n");
            } else {
                outputArea.append("Proyecto no encontrado.\n");
            }
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        } else {
            if (proyecto != null) {
                System.out.println("Detalles del Proyecto:");
                System.out.println("  ID: " + proyecto.getId());
                System.out.println("  Nombre: " + proyecto.getNombreProyecto());
                System.out.println("  Tipo de Fuente: " + proyecto.getTipoFuente());
                System.out.println("  Capacidad (MW): " + proyecto.getCapacidadMW());
            } else {
                System.out.println("Proyecto no encontrado.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        if (outputArea != null) {
            outputArea.append(mensaje + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        } else {
            System.out.println(mensaje);
        }
    }
}