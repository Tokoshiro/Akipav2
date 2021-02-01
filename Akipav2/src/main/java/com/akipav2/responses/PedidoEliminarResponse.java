package com.akipav2.responses;

public class PedidoEliminarResponse {

	private String status;
	private String mensaje;
	private String razon;
	
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
	
	public String getRazon() {
		return razon;
	}
	public void setRazon(String razon) {
		this.razon = razon;
	}
	
	public void setError(String mensaje) {
		status = "99";
		this.mensaje = mensaje;
	}
	
	public void setExito(String mensaje) {
		status = "01";
		this.mensaje = mensaje;
	}
	
}
