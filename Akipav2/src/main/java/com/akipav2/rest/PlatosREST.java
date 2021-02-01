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
import com.akipav2.responses.PlatoActualizarResponse;
import com.akipav2.responses.PlatoEliminarResponse;
import com.akipav2.responses.PlatoRegistroResponse;
import com.akipav2.responses.PlatoResponse;

@RestController
@RequestMapping("/platos")
public class PlatosREST {

	@Autowired
	private PlatosDAO platoDAO;

	// solo lista los platos 'disponibles'
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ListaPlatosResponse> getPlatos() {

		ListaPlatosResponse response = new ListaPlatosResponse();
		List<Platos> platos = platoDAO.findAllAvailablePlatos();

		response.setPlatos(platos);

		if (platos.isEmpty() || platos == null) {
			response.setError("No hay platos disponibles");
			return ResponseEntity.ok(response);
		}

		response.setExito("Solicitud Exitosa");
		return ResponseEntity.ok(response);
	}

	// obtiene un plato por su ID
	@RequestMapping(value = "{platoId}", method = RequestMethod.GET)
	public ResponseEntity<PlatoResponse> getPlatosById(@PathVariable("platoId") Long platoId) {

		PlatoResponse response = new PlatoResponse();
		Optional<Platos> optionalPlato = platoDAO.findById(platoId);

		if (optionalPlato.isPresent()) {
			response.setPlato(optionalPlato.get());
		}

		if (response.getPlato() == null) {
			response.setError("Plato no encontrado");
			return ResponseEntity.ok(response);
		} else {
			response.setExito("Solicitud Exitosa");
			return ResponseEntity.ok(response);
		}

	}

	@PostMapping()
	public ResponseEntity<PlatoRegistroResponse> createPlato(@RequestBody Platos plato) {

		PlatoRegistroResponse response = new PlatoRegistroResponse();

		// validaciones
		if (plato.getNombre() == null || plato.getNombre().isBlank()) {
			response.setError("El nombre es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (plato.getPrecio() == null) {
			response.setError("El precio es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (!plato.getPrecio().matches("^\\d\\d*(\\.\\d+)?$")) {
			response.setError("El precio debe ser numerico");
			return ResponseEntity.ok(response);
		}
		if (plato.getEstado() == null || plato.getEstado().intValue() > 1 || plato.getEstado().intValue() < 0) {
			response.setError("El estado debe ser 0: no disponible | 1: disponible");
			return ResponseEntity.ok(response);
		}

		// si llega acá es que pasó todas las validaciones
		platoDAO.save(plato);
		response.setExito("Registro exitoso");

		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "{platoId}")
	public ResponseEntity<PlatoEliminarResponse> deletePlato(@PathVariable("platoId") Long platoId) {

		PlatoEliminarResponse response = new PlatoEliminarResponse();

		// Chekamos si el plato que se quiere deshabilitar existe en la DB
		Optional<Platos> optionalPlato = platoDAO.findById(platoId);

		// Si no existe, mandamos error correspondiente
		if (!optionalPlato.isPresent()) {
			response.setError("Plato no existente en la base de datos");
			return ResponseEntity.ok(response);
		}

		// Si existe, cambiamos estado
		Platos platoDesabilitado = optionalPlato.get();
		platoDesabilitado.setEstado(0);

		platoDAO.save(platoDesabilitado);

		response.setExito("Eliminado con Exito");

		return ResponseEntity.ok(response);
	}

	@PutMapping()
	public ResponseEntity<PlatoActualizarResponse> updatePlato(@RequestBody Platos plato) {

		PlatoActualizarResponse response = new PlatoActualizarResponse();

		// validaciones

		if (plato.getId() == null) {
			response.setError("El ID es obligatorio");
			return ResponseEntity.ok(response);
		}

		Optional<Platos> optionalPlato = platoDAO.findById(plato.getId());

		if (!optionalPlato.isPresent()) {
			response.setError("Plato no existente en la base de datos");
			return ResponseEntity.ok(response);
		}

		// validaciones
		if (plato.getNombre() == null || plato.getNombre().isBlank()) {
			response.setError("El nombre es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (plato.getPrecio() == null) {
			response.setError("El precio es obligatorio");
			return ResponseEntity.ok(response);
		}
		if (!plato.getPrecio().matches("^\\d\\d*(\\.\\d+)?$")) {
			response.setError("El precio debe ser numerico");
			return ResponseEntity.ok(response);
		}
		if (plato.getEstado() == null || plato.getEstado().intValue() > 1 || plato.getEstado().intValue() < 0) {
			response.setError("El estado debe ser 0: no disponible | 1: disponible");
			return ResponseEntity.ok(response);
		}

		Platos updatePlato = optionalPlato.get();

		updatePlato.setNombre(plato.getNombre());
		updatePlato.setPrecio(plato.getPrecio());
		updatePlato.setEstado(plato.getEstado());
		updatePlato.setImagen(plato.getImagen());
		updatePlato.setDescripcionPlato(plato.getDescripcionPlato());

		updatePlato = platoDAO.save(updatePlato);

		response.setPlato(updatePlato);
		response.setExito("Actualización Realizada con Exito");
		return ResponseEntity.ok(response);
	}

}
