package com.akipav2.responses;

public class PlatoRegistroResponse {

	private String status;
	private String mensaje;
	
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
	
	public void setError(String mensaje) {
		status = "99";
		this.mensaje = mensaje;
	}
	
	public void setExito(String mensaje) {
		status = "01";
		this.mensaje = mensaje;
	}
	
}
