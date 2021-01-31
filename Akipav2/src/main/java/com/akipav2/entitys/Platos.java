package com.akipav2.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "platos")
public class Platos {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "precio", nullable = false)
	private Double precio;
	
	@Column(name = "estado", nullable = false)
	private Integer estado;
	
	@Column(name = "imagen", nullable = true, length = 500)
	private String imagen;
	
	@Column(name = "descripcion", nullable = true, length = 100)
	private String descripcionPlato;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcionPlato() {
		return descripcionPlato;
	}

	public void setDescripcionPlato(String descripcionPlato) {
		this.descripcionPlato = descripcionPlato;
	}
	
	}
