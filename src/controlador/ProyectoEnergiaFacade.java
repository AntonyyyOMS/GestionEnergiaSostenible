package controlador;

import modelo.ProyectoEnergia;
import modelo.ProyectoEnergiaDAO;
import modelo.ProyectoEnergiaFactory;

public class ProyectoEnergiaFacade {

    private ProyectoEnergiaDAO proyectoEnergiaDAO;
    private ProyectoEnergiaFactory proyectoEnergiaFactory;

    public ProyectoEnergiaFacade() {
        this.proyectoEnergiaDAO = new ProyectoEnergiaDAO();
        this.proyectoEnergiaFactory = new ProyectoEnergiaFactory();
    }

    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        ProyectoEnergia nuevoProyecto = proyectoEnergiaFactory.crearProyecto(tipoFuente);
        nuevoProyecto.setNombreProyecto(nombre);
        nuevoProyecto.setCapacidadMW(capacidad);
        // El tipoFuente ya está seteado por la factory al crear el objeto

        return proyectoEnergiaDAO.ejecutarCRUD("CREATE", nuevoProyecto);
    }
}