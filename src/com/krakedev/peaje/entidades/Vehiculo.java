package com.krakedev.peaje.entidades;

public class Vehiculo {
	private String placa;
	private String tipo;
	private Conductor propietario;
	private TagElectronico tag;
	
	public Vehiculo(String placa) {
		this.placa = placa;
		this.tipo = "L";
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Conductor getPropietario() {
		return propietario;
	}

	public void setPropietario(Conductor propietario) {
		this.propietario = propietario;
	}

	public TagElectronico getTag() {
		return tag;
	}

	public void setTag(TagElectronico tag) {
		this.tag = tag;
	}
	
	public void imprimir () {
		System.out.println("Placa: " + placa);
		System.out.println("Tipo " + tipo);
	}
}