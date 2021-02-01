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
import com.akipav2.responses.PedidoActualizarResponse;
import com.akipav2.responses.PedidoEliminarResponse;
import com.akipav2.responses.PedidoRegistroResponse;
import com.akipav2.responses.PedidoResponse;
import com.akipav2.utils.Razon;

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
		// He quitado la validación del Estado, debido a que cuando se ingrese un pedido
		// esté estará 'en espera' por defecto. 2 -> en espera
		pedido.setEstado(2);
		
		//validaciones correctas
		pedidoDAO.save(pedido);
		response.setExito("Registro exitoso");
		
		return ResponseEntity.ok(response);
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