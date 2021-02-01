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
import com.akipav2.responses.ListaPedidoResponse;
import com.akipav2.responses.PedidoRegistroResponse;
import com.akipav2.responses.PedidoResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidosREST {

	@Autowired
	private PedidosDAO pedidoDAO;
	
	//listar pedidos disponibles y espera
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ListaPedidoResponse> getPedido(){
		
		ListaPedidoResponse response = new ListaPedidoResponse();
		List<Pedido> pedidos = pedidoDAO.findAllAvailablePedidos();
		
		response.setPedido(pedidos);
		
		//validamos pedidos existentes
		if (pedidos.isEmpty() || pedidos==null) {
			response.setError("No hay pedidos disponibles");
			return ResponseEntity.ok(response);
		}
		//validacion correcta
		response.setExito("Solicitud Exitosa");
		return ResponseEntity.ok(response);
	}
	
	//Listar pedido por id
	@RequestMapping(value = "{pedidoId}", method = RequestMethod.GET)
	public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable("pedidoId") Long pedidoId){
		
		PedidoResponse response = new PedidoResponse();
		Optional<Pedido> optionalPedido =pedidoDAO.findById(pedidoId);
		
		//optenemos pedidos 
		if (optionalPedido.isPresent()) {
			response.setPedido(optionalPedido.get());
				
		}
		//validamos pedidos 
		if (response.getPedido()==null){
			response.setError("Pedido no encontrado");
			return ResponseEntity.ok(response);
		}
		//validacion correcta
		else {
			response.setExito("Solicitus Exitosa");
			return ResponseEntity.ok(response);
		}
		
	}
	
	//Registrar pedido
	@PostMapping()
	public ResponseEntity<PedidoRegistroResponse> createPedido(@RequestBody Pedido pedido){
		
		PedidoRegistroResponse response = new PedidoRegistroResponse();
		
		//validaciones
		if (pedido.getNombre()==null || pedido.getNombre().isBlank()) {
			response.setError("El nombre es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (pedido.getDireccion()==null || pedido.getDireccion().isBlank()) {
			response.setError("La direccion es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (pedido.getCelular()==null) {
			response.setError("El numero celular es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (!pedido.getCelular().matches("^\\d\\d*(\\.\\d+)?$")) {
			response.setError("El celular debe ser numerico");
			return ResponseEntity.ok(response);
		}
		if (!pedido.getCelular().startsWith("9")) {
			response.setError("El celular debe iniciar con 9");
			return ResponseEntity.ok(response);
		}
		if (pedido.getEstado()==null || pedido.getEstado().intValue()>3 
									|| pedido.getEstado().intValue()<0) {
			response.setError("El estado debe ser 0: rechazado | 1: en espera | 2: rechazado");
			return ResponseEntity.ok(response);
			
		}
		
		//validaciones correctas
		pedidoDAO.save(pedido);
		response.setExito("Registro exitoso");
		
		return ResponseEntity.ok(response);
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
