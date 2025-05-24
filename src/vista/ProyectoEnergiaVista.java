package vista;

import modelo.ProyectoEnergia;

public class ProyectoEnergiaVista {

    public void mostrarDetallesProyecto(ProyectoEnergia proyecto) {
        if (proyecto != null) {
            System.out.println("Detalles del Proyecto:");
            System.out.println("ID: " + proyecto.getId());
            System.out.println("Nombre: " + proyecto.getNombreProyecto());
            System.out.println("Tipo de Fuente: " + proyecto.getTipoFuente());
            System.out.println("Capacidad (MW): " + proyecto.getCapacidadMW());
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}