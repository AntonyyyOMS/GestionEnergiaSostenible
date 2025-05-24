package modelo;

import java.util.List; // Aunque no se usa directamente en este Facade, es común para listas

public class ProyectoEnergiaFacade {

    private ProyectoEnergiaDAO proyectoEnergiaDAO;

    public ProyectoEnergiaFacade() {
        this.proyectoEnergiaDAO = new ProyectoEnergiaDAO();
    }

    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        ProyectoEnergia proyecto = ProyectoEnergiaFactory.crearProyecto(tipoFuente);
        proyecto.setNombreProyecto(nombre);
        proyecto.setTipoFuente(tipoFuente);
        proyecto.setCapacidadMW(capacidad);
        return proyectoEnergiaDAO.ejecutarCRUD("CREATE", proyecto);
    }

    public ProyectoEnergia obtenerProyectoEnergia(int id, String tipoFuente) {
        // Creamos un objeto ProyectoEnergia con el ID que queremos buscar
        // y el tipoFuente para que la factory pueda crear el objeto correcto (aunque no se usa el tipo en el DAO para READ)
        ProyectoEnergia proyecto = ProyectoEnergiaFactory.crearProyecto(tipoFuente);
        proyecto.setId(id);

        // Ejecutamos la operación READ. El DAO modificará el objeto 'proyecto' si lo encuentra.
        int resultado = proyectoEnergiaDAO.ejecutarCRUD("READ", proyecto);

        // Si resultado > 0, significa que se encontró y se actualizó el objeto 'proyecto'.
        // Si no se encontró, 'proyecto' seguirá teniendo solo el ID y el tipoFuente iniciales.
        if (resultado > 0) {
            return proyecto; // Retornamos el objeto con los datos de la BD
        } else {
            return null; // O lanzamos una excepción si no se encontró
        }
    }

    public int actualizarProyectoEnergia(int id, String nombre, String tipoFuente, double capacidad) {
        ProyectoEnergia proyecto = ProyectoEnergiaFactory.crearProyecto(tipoFuente);
        proyecto.setId(id);
        proyecto.setNombreProyecto(nombre);
        proyecto.setTipoFuente(tipoFuente);
        proyecto.setCapacidadMW(capacidad);
        return proyectoEnergiaDAO.ejecutarCRUD("UPDATE", proyecto);
    }

    public int eliminarProyectoEnergia(int id, String tipoFuente) {
        ProyectoEnergia proyecto = ProyectoEnergiaFactory.crearProyecto(tipoFuente);
        proyecto.setId(id);
        return proyectoEnergiaDAO.ejecutarCRUD("DELETE", proyecto);
    }
}