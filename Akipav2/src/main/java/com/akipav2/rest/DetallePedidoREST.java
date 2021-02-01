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

import com.akipav2.dao.DetallePedidoDAO;
import com.akipav2.dao.PlatosDAO;
import com.akipav2.entitys.DetallePedido;
import com.akipav2.responses.DetallePedidoEliminarResponse;
import com.akipav2.responses.DetallePedidoRegistroResponse;
import com.akipav2.responses.DetallePedidoResponse;
import com.akipav2.responses.ListaDetallePedidoResponse;
import com.akipav2.responses.PlatoRegistroResponse;

import io.swagger.models.Response;

@RestController
@RequestMapping("/detallePedido")
public class DetallePedidoREST {

	@Autowired
	private DetallePedidoDAO detalleDAO;
	
	//lista todos los datos de la tabla DetallePedido
	@RequestMapping(method = RequestMethod.GET)
	public  ResponseEntity<ListaDetallePedidoResponse> getDetalle(){
		
		ListaDetallePedidoResponse response = new ListaDetallePedidoResponse();
		List<DetallePedido> detalle = detalleDAO.findAllAvailableDetalles();
		
		response.setDetallePedido(detalle);
		
		//validamos que detalle exista
		if (detalle.isEmpty() || detalle==null) {
			response.setStatus("99");
			response.setMensaje("No existen detalles de pedidos");
			return ResponseEntity.ok(response);
		}
		//validacion correcta
		response.setStatus("01");
		response.setMensaje("Solicitud Exitosa");
		return ResponseEntity.ok(response);
	}
	
	//obtiene detalle plato por ID
	@RequestMapping(value = "{detalleId}", method = RequestMethod.GET)
	public ResponseEntity<DetallePedidoResponse> getDetalleById(@PathVariable("detalleId")Long detalleId){
		
		DetallePedidoResponse response = new DetallePedidoResponse();
		Optional<DetallePedido> optionalDetalle = detalleDAO.findById(detalleId);
		
		//optenemos detalle por id
		if (optionalDetalle.isPresent()) {
			
			response.setDetallePedido(optionalDetalle.get());
			
		} 
		//validamos si existe detalle 
		if (response.getDetallePedido()==null) {
			
			response.setStatus("99");
			response.setMensaje("Detalle de plato no encontrado");
			return ResponseEntity.ok(response);
			
		}
		//validacion correcta
		else {				
			response.setStatus("01");
			response.setMensaje("Solicitud exitosa");
			return ResponseEntity.ok(response);
			
		}		
	}
	
	//registrar detalle pedido
	@PostMapping()
	public ResponseEntity<DetallePedidoRegistroResponse> createDetalle(@RequestBody DetallePedido detalle){
		
		DetallePedidoRegistroResponse response = new DetallePedidoRegistroResponse();
		
		//Validadicones
		
		//validamos id del pedido
		if (detalle.getIdpedido()==null || detalle.getIdpedido()<=0) {
			response.setError("Id del pedido inexistente o menor a 0");
			return ResponseEntity.ok(response);
		} 
		//validamos id del plato 
		else if (detalle.getIdplato()==null || detalle.getIdplato()<=0) {
			response.setError("Id del plato inexistente o menor a 0");
			return ResponseEntity.ok(response);
		} 
		//validamos cantidad
		else if(detalle.getCantidad()==null || detalle.getCantidad()<=0) {
			response.setError("Se necesita agregar un plato");
			return ResponseEntity.ok(response);
		}
		// validaciones correctas
		detalleDAO.save(detalle);
		response.setExito("Registro exitoso");
		
		return ResponseEntity.ok(response);
	}
	
	
	//eliminar detalle no necesario
	/*eliminar detalle pedido
	@DeleteMapping(value = "{detalleId}")
	public ResponseEntity<DetallePedidoEliminarResponse> deleteDetalle(@PathVariable("detalleId")Long detalleId){
		
		DetallePedidoEliminarResponse response = new DetallePedidoEliminarResponse();
		
		//obtenemos el detalle
		Optional<DetallePedido> optionalDetalle = detalleDAO.findById(detalleId);
  
		//validamos si existe detalle
		if (!optionalDetalle.isPresent()) {
			response.setError("Detalle no existente");
			return ResponseEntity.ok(response);
		}
		
		//validacion correcta
		else {
			detalleDAO.deleteById(detalleId);
			
			response.setStatus("01");
			response.setMensaje("Solicitud exitosa");
			return ResponseEntity.ok(response);	
		}
	}*/
	
	//actualizar detalle no necesario
	/*@PutMapping()
	public ResponseEntity<DetallePedido> updateDetalle(@RequestBody DetallePedido detalle){
		return null;
	}*/
	
}
