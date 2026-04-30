package model;

public class Mesa {

    public enum EstadoMesa {
        disponible,
        reservada,
        mantenimiento
    }

    private int idMesa;
    private EstadoMesa estadoMesa;

    public Mesa() {

    }

    public Mesa(int idMesa, EstadoMesa estadoMesa) {
        this.idMesa = idMesa;
        this.estadoMesa = estadoMesa;
    }

    @Override
    public String toString() {
        return "\n--- MESA ---" +
                "\nID Mesa: " + idMesa +
                "\nEstado: " + estadoMesa;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public EstadoMesa getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(EstadoMesa estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
}