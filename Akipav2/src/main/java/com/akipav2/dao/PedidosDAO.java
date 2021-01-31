package com.akipav2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akipav2.entitys.Pedido;

public interface PedidosDAO extends JpaRepository<Pedido, Long>{

}
