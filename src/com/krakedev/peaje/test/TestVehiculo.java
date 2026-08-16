package com.krakedev.peaje.test;

import com.krakedev.peaje.entidades.Conductor;
import com.krakedev.peaje.entidades.TagElectronico;
import com.krakedev.peaje.entidades.Vehiculo;
import com.krakedev.peaje.servicios.EstacionPeaje;
import com.krakedev.peaje.util.ImpresorUtil;

public class TestVehiculo {
    public static void main(String[] args) {
        // 1. Crear conductor
        Conductor conductor1 = new Conductor("2026081001", "Ana", "Perez");

        // 2. Crear vehiculo
        Vehiculo vehiculo1 = new Vehiculo("PBX-1234");

        // 3. Crear tag
        TagElectronico tag1 = new TagElectronico("TAG-001");

        // 4. Asociar objetos mediante composicion
        vehiculo1.setPropietario(conductor1);
        vehiculo1.setTag(tag1);

System.out.println("----- ESTADO INICIAL DEL VEHICULO -----");
		// 5. Ejecutar imprimir()
		vehiculo1.imprimir();

		System.out.println("----- INFORMACION COMPLETA -----");
		// 6. Ejecutar ImpresorUtil.imprimirVehiculo()
		ImpresorUtil.imprimirVehiculo(vehiculo1);

		// 7. Validar metodos de negocio
		EstacionPeaje estacion = new EstacionPeaje();
		boolean resultado;

		System.out.println("----- REGISTRAR VEHICULO TIPO VALIDO -----");
		Conductor conductor2 = new Conductor("2026081002", "Luis", "Gomez");
		Vehiculo vehiculo2 = estacion.registrarVehiculo("PBX-5678", "L", conductor2, "TAG-002");
		System.out.println("Vehiculo registrado: " + vehiculo2);
		if (vehiculo2 != null) {
			vehiculo2.imprimir();
		}

		System.out.println("----- REGISTRAR VEHICULO TIPO INVALIDO -----");
		Conductor conductor3 = new Conductor("2026081003", "Marta", "Diaz");
		Vehiculo vehiculo3 = estacion.registrarVehiculo("PBX-9999", "Z", conductor3, "TAG-003");
		System.out.println("Vehiculo registrado: " + vehiculo3);

		System.out.println("----- RECARGA VALIDA -----");
		resultado = estacion.recargarTag(tag1, 20.0);
		System.out.println("¿Se recargo correctamente? " + resultado);
		tag1.imprimir();

		System.out.println("----- RECARGA INVALIDA (monto negativo) -----");
		resultado = estacion.recargarTag(tag1, -5.0);
		System.out.println("¿Se recargo correctamente? " + resultado);
		tag1.imprimir();

		System.out.println("----- COBRAR PEAJE CON SALDO SUFICIENTE -----");
		resultado = estacion.cobrarPeaje(vehiculo1);
		System.out.println("¿Se cobro correctamente? " + resultado);
		tag1.imprimir();

		System.out.println("----- COBRAR PEAJE SIN SALDO SUFICIENTE -----");
		Conductor conductor4 = new Conductor("2026081004", "Pedro", "Lopez");
		Vehiculo vehiculo4 = estacion.registrarVehiculo("PBX-7777", "P", conductor4, "TAG-004");
		resultado = estacion.cobrarPeaje(vehiculo4);
		System.out.println("¿Se cobro correctamente? " + resultado);
		vehiculo4.getTag().imprimir();

		System.out.println("----- TRANSFERENCIA VALIDA -----");
		Conductor conductor5 = new Conductor("2026081005", "Vero", "Nunez");
		Vehiculo vehiculo5 = estacion.registrarVehiculo("PBX-3333", "L", conductor5, "TAG-005");
		estacion.recargarTag(vehiculo5.getTag(), 15.0);
		resultado = estacion.transferirSaldoTag(tag1, vehiculo5.getTag(), 5.0);
		System.out.println("¿Se transfirio correctamente? " + resultado);
		tag1.imprimir();
		vehiculo5.getTag().imprimir();

		System.out.println("----- TRANSFERENCIA INVALIDA (saldo insuficiente) -----");
		resultado = estacion.transferirSaldoTag(tag1, vehiculo5.getTag(), 999.0);
		System.out.println("¿Se transfirio correctamente? " + resultado);
		tag1.imprimir();
		vehiculo5.getTag().imprimir();

	}
}
