package model;

import java.time.LocalDateTime;

public class Reserva {
    private Usuario usuario;
    private Mesa mesa;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private double coste;
    private String estadoReserva;

    public Reserva() {

    }

    public Reserva(Usuario usuario, Mesa mesa, LocalDateTime horaInicio, LocalDateTime horaFin, double coste, String estadoReserva) {
        this.usuario = usuario;
        this.mesa = mesa;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.coste = coste;
        this.estadoReserva = estadoReserva;
    }

    @Override
    public String toString() {
        return  "\n--- RESERVA ---" +
                "\nID Usuario: " + usuario.getIdUsuario() +
                "\nNombre: " + usuario.getNombre() + " " + usuario.getApellidos() +
                "\nID Mesa: " + mesa.getIdMesa() +
                "\nHora de inicio: " + horaInicio +
                "\nHora de fin: " + horaFin +
                "\nCoste: " + coste + "€" +
                "\nEstado: " + estadoReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
