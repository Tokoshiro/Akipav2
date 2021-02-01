package com.akipav2.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.akipav2.dao.PlatosDAO;
import com.akipav2.entitys.Platos;
import com.akipav2.responses.ListaPlatosResponse;

@RestController
@RequestMapping("/platos")
public class PlatosREST {

	@Autowired
	private PlatosDAO platoDAO;
	
	// solo lista los platos 'disponibles'
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ListaPlatosResponse> getPlatos(){
		
		ListaPlatosResponse response = new ListaPlatosResponse();
		List<Platos> platos = platoDAO.findAllAvailablePlatos();

		response.setPlatos(platos);
		
		if(platos.isEmpty() || platos == null) {
			response.setStatus("00");
			response.setMensaje("No hay platos disponibles");
			return ResponseEntity.ok(response);
		}
		
		response.setStatus("01");
		response.setMensaje("Solicitud Exitosa");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "{platoId}", method = RequestMethod.GET)
	public ResponseEntity<Platos> getPlatosById(@PathVariable("platoId") Long platoId){
		
		Optional<Platos> optionalPlatos =platoDAO.findById(platoId);
		if (optionalPlatos.isPresent()) {
			return ResponseEntity.ok(optionalPlatos.get());	
		}else {
			return ResponseEntity.noContent().build();
		}
		
	}
	
	@PostMapping()
	public ResponseEntity<Platos> createPlato(@RequestBody Platos plato){
		Platos newPlato = platoDAO.save(plato);
		return ResponseEntity.ok(newPlato);
		
	}
	
	@DeleteMapping(value = "{platoId}")
	public ResponseEntity<Void> deletePlato(@PathVariable("platoId") Long platoId){
		platoDAO.deleteById(platoId);
		return ResponseEntity.ok(null);
		
	}
	
	@PutMapping()
	public ResponseEntity<Platos> updatePlato(@RequestBody Platos plato){
		Optional<Platos> optionalPlato = platoDAO.findById(plato.getId());
		if (optionalPlato.isPresent()) {
			
			Platos updatePlato = optionalPlato.get();
			
			updatePlato.setNombre(plato.getNombre());
			updatePlato.setPrecio(plato.getPrecio());
			updatePlato.setEstado(plato.getEstado());
			updatePlato.setImagen(plato.getImagen());
			updatePlato.setDescripcionPlato(plato.getDescripcionPlato());
			
			platoDAO.save(updatePlato);
			
			return ResponseEntity.ok(updatePlato);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
		
}
