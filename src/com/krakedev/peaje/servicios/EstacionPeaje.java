package com.krakedev.peaje.servicios;

public class EstacionPeaje {
    private int codigoEstacion;
    private double tarifaLiviano;
    private double tarifaPesado;

    public EstacionPeaje() {
        this.codigoEstacion = 500;
        this.tarifaLiviano = 1.00;
        this.tarifaPesado = 2.50;
    }

    public int getCodigoEstacion() {
        return codigoEstacion;
    }

    public void setCodigoEstacion(int codigoEstacion) {
        this.codigoEstacion = codigoEstacion;
    }

    public double getTarifaLiviano() {
        return tarifaLiviano;
    }

    public void setTarifaLiviano(double tarifaLiviano) {
        this.tarifaLiviano = tarifaLiviano;
    }

    public double getTarifaPesado() {
        return tarifaPesado;
    }

    public void setTarifaPesado(double tarifaPesado) {
        this.tarifaPesado = tarifaPesado;
    }
}