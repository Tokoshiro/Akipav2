package com.akipav2.responses;

import com.akipav2.entitys.Platos;

public class PlatoResponse {

	private String status;
	private String mensaje;
	private Platos plato;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Platos getPlato() {
		return plato;
	}
	public void setPlato(Platos plato) {
		this.plato = plato;
	}
	
}
