package com.akipav2.responses;

import com.akipav2.entitys.DetallePedido;

public class DetallePedidoResponse {
	
	private String status;
	private String mensaje;
	private DetallePedido DetallePedido;
	
	
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
	public DetallePedido getDetallePedido() {
		return DetallePedido;
	}
	public void setDetallePedido(DetallePedido detallePedido) {
		DetallePedido = detallePedido;
	}


}
