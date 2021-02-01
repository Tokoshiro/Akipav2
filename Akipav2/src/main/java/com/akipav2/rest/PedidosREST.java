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
import com.akipav2.responses.PedidoActualizarResponse;
import com.akipav2.responses.PedidoEliminarResponse;
import com.akipav2.utils.Razon;

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
	
	@PutMapping(value = "{pedidoId}/rechazar")
	public ResponseEntity<PedidoEliminarResponse> deletePedido(@RequestBody Razon razon, @PathVariable("pedidoId") Long pedidoId){
		
		PedidoEliminarResponse response = new PedidoEliminarResponse();
		Optional<Pedido> pedidoOptional = pedidoDAO.findById(pedidoId);
		
		if(razon == null || razon.getRazon() == null || razon.getRazon().length() == 0) {
			response.setError("Debe especificar una razón");
			return ResponseEntity.ok(response);
		}
		
		if (!pedidoOptional.isPresent()) {
			response.setError("No existe el pedido en la base de datos");
			response.setRazon(null);
			return ResponseEntity.ok(response);
		}
		
		Pedido pedido = pedidoOptional.get();
		pedido.setEstado(0);
		
		pedidoDAO.save(pedido);
		
		response.setExito("Pedido Rechazado");
		response.setRazon(razon.getRazon());
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping(value = "{pedidoId}/aceptar")
	public ResponseEntity<PedidoActualizarResponse> aceptarPedido(@PathVariable("pedidoId") Long pedidoId){
		
		PedidoActualizarResponse response = new PedidoActualizarResponse();
		Optional<Pedido> pedidoOptional = pedidoDAO.findById(pedidoId);
		
		if (!pedidoOptional.isPresent()) {
			response.setError("No existe el pedido en la base de datos");
			return ResponseEntity.ok(response);
		}
		
		Pedido pedido = pedidoOptional.get();
		pedido.setEstado(1);
		
		pedidoDAO.save(pedido);
		response.setExito("Pedido Aceptado");
		return ResponseEntity.ok(response);
	}
	
}
