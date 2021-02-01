package com.akipav2.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DetallePedido")
public class DetallePedido {

	@Id
	@Column(name = "iddetallepedido")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iddetallepedido;
	
	@Column(name = "idplato", nullable = false)
	private Long idplato;
	
	@Column(name = "idpedido", nullable = false)
	private Long idpedido;
	
	@Column(name = "Cantidad", nullable = false)
	private Integer Cantidad;
	
	
	public Long getIddetallepedido() {
		return iddetallepedido;
	}

	public void setIddetallepedido(Long iddetallepedido) {
		this.iddetallepedido = iddetallepedido;
	}

	public Long getIdplato() {
		return idplato;
	}

	public void setIdplato(Long idplato) {
		this.idplato = idplato;
	}

	public Long getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(Long idpedido) {
		this.idpedido = idpedido;
	}

	public Integer getCantidad() {
		return Cantidad;
	}

	public void setCantidad(Integer cantidad) {
		Cantidad = cantidad;
	}

}
