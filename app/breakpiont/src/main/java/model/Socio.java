package model;

import java.time.LocalDateTime;

public class Socio {
    private int idSocio;
    private Usuario usuario;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private String estadoSocio;

    public Socio() {

    }

    public Socio(int idSocio, Usuario usuario, LocalDateTime fechaAlta, LocalDateTime fechaBaja, String estadoSocio) {
        this.idSocio = idSocio;
        this.usuario = usuario;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estadoSocio = estadoSocio;
    }

    @Override
    public String toString() {
        return  "\n--- SOCIO ---" +
                "\nID Socio: " + idSocio +
                "\nID Usuario: " + usuario.getIdUsuario() +
                "\nNombre: " + usuario.getNombre() + " " + usuario.getApellidos() +
                "\nFecha de alta: " + fechaAlta +
                "\nFecha de baja: " + (fechaBaja != null ? fechaBaja : "Activo") +
                "\nEstado: " + estadoSocio;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getEstadoSocio() {
        return estadoSocio;
    }

    public void setEstadoSocio(String estadoSocio) {
        this.estadoSocio = estadoSocio;
    }
}
