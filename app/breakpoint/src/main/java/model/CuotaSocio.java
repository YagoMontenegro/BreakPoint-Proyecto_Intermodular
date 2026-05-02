package model;

import java.time.LocalDateTime;

public class CuotaSocio {
    private int idCuota;
    private Socio socio;
    private LocalDateTime fechaPago;
    private int mes;
    private int anio;
    private String estadoCuota;
    private double importe;

    public CuotaSocio() {

    }

    public CuotaSocio(int idCuota, Socio socio, LocalDateTime fechaPago, int mes, int anio, String estadoCuota, double importe) {
        this.idCuota = idCuota;
        this.socio = socio;
        this.fechaPago = fechaPago;
        this.mes = mes;
        this.anio = anio;
        this.estadoCuota = estadoCuota;
        this.importe = importe;
    }

    @Override
    public String toString() {
        return  "\n--- CUOTA DE SOCIO ---" +
                "\nID Cuota: " + idCuota +
                "\nID Socio: " + socio.getIdSocio() +
                "\nNombre socio: " + socio.getNombre() + " " + socio.getApellidos() +
                "\nFecha de pago: " + (fechaPago != null ? fechaPago : "Pendiente de pago") +
                "\nMes: " + mes +
                "\nAño: " + anio +
                "\nEstado: " + estadoCuota +
                "\nImporte: " + importe + "€";
    }

    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(String estadoCuota) {
        this.estadoCuota = estadoCuota;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
