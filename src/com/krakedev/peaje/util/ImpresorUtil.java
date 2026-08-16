package com.krakedev.peaje.util;

import com.krakedev.peaje.entidades.Vehiculo;

public class ImpresorUtil {

    public static void imprimirVehiculo(Vehiculo vehiculo) {
        String cedula = vehiculo.getPropietario().getCedula();
        String nombre = vehiculo.getPropietario().getNombre();
        String apellido = vehiculo.getPropietario().getApellido();

        String placa = vehiculo.getPlaca();
        String tipo = vehiculo.getTipo();
        String idTag = vehiculo.getTag().getIdTag();
        double saldo = vehiculo.getTag().getSaldo();
        boolean activo = vehiculo.getTag().isActivo();

        System.out.println("--- Vehiculo ---");
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + tipo);

        System.out.println("--- Propietario ---");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre + " " + apellido);

        System.out.println("--- Tag Electronico ---");
        System.out.println("Tag: " + idTag);
        System.out.println("Activo: " + activo);
        System.out.println("Saldo disponible: " + saldo);
    }
}