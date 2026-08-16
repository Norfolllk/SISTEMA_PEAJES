package com.krakedev.peaje.servicios;

import com.krakedev.peaje.entidades.Conductor;
import com.krakedev.peaje.entidades.TagElectronico;
import com.krakedev.peaje.entidades.Vehiculo;
import com.krakedev.peaje.util.ValidadorUtil;

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

    public Vehiculo registrarVehiculo(String placa, String tipo, Conductor conductor, String idTag) {
        boolean tipoValido = ValidadorUtil.esTipoValido(tipo);
        if (tipoValido == false) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo(placa);
        vehiculo.setTipo(tipo);
        vehiculo.setPropietario(conductor);

        TagElectronico tag = new TagElectronico(idTag);
        vehiculo.setTag(tag);
        return vehiculo;
    }

    public boolean recargarTag(TagElectronico tag, double monto) {
        boolean montoValido = ValidadorUtil.esMontoValido(monto);
        if (montoValido == false) {
            return false;
        }
        double saldoActual = tag.getSaldo();
        tag.setSaldo(saldoActual + monto);
        return true;
    }

    public boolean cobrarPeaje(Vehiculo vehiculo) {
        String tipo = vehiculo.getTipo();
        double tarifa;
        if (tipo == "L") {
            tarifa = tarifaLiviano;
        } else {
            tarifa = tarifaPesado;
        }

        TagElectronico tag = vehiculo.getTag();
        double saldoActual = tag.getSaldo();

        if (saldoActual < tarifa) {
            return false;
        }

        tag.setSaldo(saldoActual - tarifa);
        return true;
    }

    public boolean transferirSaldoTag(TagElectronico origen, TagElectronico destino, double monto) {
        boolean montoValido = ValidadorUtil.esMontoValido(monto);
        if (montoValido == false) {
            return false;
        }

        double saldoOrigen = origen.getSaldo();
        if (saldoOrigen < monto) {
            return false;
        }
        origen.setSaldo(saldoOrigen - monto);

        double saldoDestino = destino.getSaldo();
        destino.setSaldo(saldoDestino + monto);
        return true;
    }
}