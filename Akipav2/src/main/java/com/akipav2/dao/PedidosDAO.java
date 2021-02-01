package com.akipav2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.akipav2.entitys.Pedido;

public interface PedidosDAO extends JpaRepository<Pedido, Long>{
	
	@Query("SELECT p FROM Pedidos Where p.estado = 1 and 3")
	List<Pedido> findAllAvailablePedidos();

}
