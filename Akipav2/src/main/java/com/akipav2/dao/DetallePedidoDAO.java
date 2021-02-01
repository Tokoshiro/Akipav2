package com.akipav2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.akipav2.entitys.DetallePedido;

public interface DetallePedidoDAO extends JpaRepository<DetallePedido, Long>{

	@Query("SELECT d FROM DetallePedido d WHERE d.idpedido = ?1")
	List<DetallePedido> getDetalleDelPedido(Long idPedido);
}
