package model;

import java.time.LocalDateTime;

public class Torneo {
    private int idTorneo;
    private String nombre;
    private String modalidad;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int maxParticipantes;
    private String premios;
    private String estadoTorneo;

    public Torneo() {

    }

    public Torneo(int idTorneo, String nombre, String modalidad, LocalDateTime fechaInicio, LocalDateTime fechaFin, int maxParticipantes, String premios, String estadoTorneo) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.modalidad = modalidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.maxParticipantes = maxParticipantes;
        this.premios = premios;
        this.estadoTorneo = estadoTorneo;
    }

    @Override
    public String toString() {
        return  "\n--- TORNEO ---" +
                "\nID Torneo: " + idTorneo +
                "\nNombre: " + nombre +
                "\nModalidad: " + modalidad +
                "\nFecha de inicio: " + fechaInicio +
                "\nFecha de fin: " + (fechaFin != null ? fechaFin : "En curso") +
                "\nMáx. participantes: " + maxParticipantes +
                "\nPremios: " + premios +
                "\nEstado: " + estadoTorneo;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public void setMaxParticipantes(int maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public String getEstadoTorneo() {
        return estadoTorneo;
    }

    public void setEstadoTorneo(String estadoTorneo) {
        this.estadoTorneo = estadoTorneo;
    }
}
