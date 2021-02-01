package com.akipav2.responses;

import java.util.List;

import com.akipav2.entitys.Platos;

public class ListaPlatosResponse {

	private String status;
	private String mensaje;
	private List<Platos> platos;
	
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
	public List<Platos> getPlatos() {
		return platos;
	}
	public void setPlatos(List<Platos> platos) {
		this.platos = platos;
	}
	
}
