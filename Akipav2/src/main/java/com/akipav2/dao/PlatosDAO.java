package com.akipav2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.akipav2.entitys.Platos;

public interface PlatosDAO extends JpaRepository<Platos, Long>{
	
	@Query("SELECT p FROM Platos p WHERE p.estado = 1")
	List<Platos> findAllAvailablePlatos();

}
