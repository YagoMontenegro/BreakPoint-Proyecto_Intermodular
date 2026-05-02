package model;

import java.time.LocalDateTime;

public class Inscripcion {
    private Socio socio;
    private Torneo torneo;
    private LocalDateTime fechaInscripcion;
    private Integer resultado;

    public Inscripcion() {

    }

    public Inscripcion(Socio socio, Torneo torneo, LocalDateTime fechaInscripcion, Integer resultado) {
        this.socio = socio;
        this.torneo = torneo;
        this.fechaInscripcion = fechaInscripcion;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return  "\n--- INSCRIPCIÓN ---" +
                "\nID Socio: " + socio.getIdSocio() +
                "\nNombre socio: " + socio.getNombre() + " " + socio.getApellidos() +
                "\nID Torneo: " + torneo.getIdTorneo() +
                "\nTorneo: " + torneo.getNombre() +
                "\nFecha de inscripción: " + fechaInscripcion +
                "\nResultado: " + (resultado != null ? resultado : "Pendiente de resolución");
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }
}
