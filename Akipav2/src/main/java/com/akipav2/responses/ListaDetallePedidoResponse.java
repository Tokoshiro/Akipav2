package com.akipav2.responses;

import java.util.List;

import com.akipav2.entitys.DetallePedido;
import com.akipav2.utils.DetallePorPlato;

public class ListaDetallePedidoResponse {

	private String status;
	private String mensaje;
	private List<DetallePorPlato> detallePorPlato;
	
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
	public List<DetallePorPlato> getDetallePedido() {
		return detallePorPlato;
	}
	public void setDetallePedido(List<DetallePorPlato> detallePedido) {
		this.detallePorPlato = detallePedido;
	}
	
}
