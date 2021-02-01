package com.akipav2.responses;

import java.util.List;

import com.akipav2.entitys.DetallePedido;

public class ListaDetallePedidoResponse {

	private String status;
	private String mensaje;
	private List<DetallePedido> detallePedido;
	
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
	public List<DetallePedido> getDetallePedido() {
		return detallePedido;
	}
	public void setDetallePedido(List<DetallePedido> detallePedido) {
		this.detallePedido = detallePedido;
	}
	
}
