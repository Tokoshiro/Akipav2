package com.akipav2.responses;

import java.util.List;

import com.akipav2.entitys.Pedido;

public class ListaPedidoResponse {
	
	private String status;
	private String mensaje;
	private List<Pedido> pedido;
	
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
	public List<Pedido> getPedido() {
		return pedido;
	}
	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
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
