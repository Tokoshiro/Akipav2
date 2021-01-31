package com.akipav2.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akipav2.dao.PedidosDAO;
import com.akipav2.entitys.Pedido;

@RestController
@RequestMapping("/pedidos")
public class PedidosREST {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String hello() {
		return "Estas en la seccion pedidos";
	}

	@Autowired
	private PedidosDAO pedidoDAO;
	
	@RequestMapping(value = "listar", method = RequestMethod.GET)
	public ResponseEntity<List<Pedido>> getPedido(){
		List<Pedido> pedidos = pedidoDAO.findAll();
		return ResponseEntity.ok(pedidos);
	}
	
	@RequestMapping(value = "listar/{pedidoId}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> getPedidoById(@PathVariable("pedidoId") Long pedidoId){
		Optional<Pedido> optionalPedido =pedidoDAO.findById(pedidoId);
		if (optionalPedido.isPresent()) {
			return ResponseEntity.ok(optionalPedido.get());	
		}else {
			return ResponseEntity.noContent().build();
		}
		
	}
	
	@PostMapping(value = "/registrar")
	public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido){
		Pedido newPedido = pedidoDAO.save(pedido);
		return ResponseEntity.ok(newPedido);
		
	}
	
	@DeleteMapping(value = "/eliminar/{pedidoId}")
	public ResponseEntity<Void> deletePedido(@PathVariable("pedidoId") Long pedidoId){
		pedidoDAO.deleteById(pedidoId);
		return ResponseEntity.ok(null);
		
	}
	
	@PutMapping(value = "/actualizar")
	public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido pedido){
		Optional<Pedido> optionalPedido = pedidoDAO.findById(pedido.getId());
		if (optionalPedido.isPresent()) {
			
			Pedido updatePedido = optionalPedido.get();
			
			updatePedido.setNombre(pedido.getNombre());
			updatePedido.setDireccion(pedido.getDireccion());
			updatePedido.setReferencia(pedido.getReferencia());
			updatePedido.setCelular(pedido.getCelular());
			updatePedido.setEstado(pedido.getEstado());
			
			pedidoDAO.save(updatePedido);
			
			return ResponseEntity.ok(updatePedido);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
