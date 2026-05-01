package model;

import java.time.LocalDateTime;

public class Socio extends Usuario {
    private int idSocio;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;
    private String estadoSocio;

    public Socio() {

    }

    public Socio(int idSocio, int idUsuario, String nombre, String apellidos, String email, String telefono,
                 LocalDateTime fechaRegistro, LocalDateTime fechaAlta, LocalDateTime fechaBaja, String estadoSocio) {
        super(idUsuario, nombre, apellidos, email, telefono, fechaRegistro);
        this.idSocio = idSocio;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estadoSocio = estadoSocio;
    }

    @Override
    public String toString() {
        return  "\n--- SOCIO ---" +
                "\nID Socio: " + idSocio +
                "\nID Usuario: " + getIdUsuario() +
                "\nNombre: " + getNombre() + " " + getApellidos() +
                "\nEmail: " + getEmail() +
                "\nTeléfono: " + getTelefono() +
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
