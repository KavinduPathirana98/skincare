package com.skincare.api.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skincare.api.DTO.ResponseDTO;
import com.skincare.api.DTO.TreatmentDTO;
import com.skincare.api.IService.ITreatmentService;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentController {

	@Autowired
	ITreatmentService service;

	// Get All Treatment
	@GetMapping
	public ResponseDTO getAllTreatments() {

		try {
			List<TreatmentDTO> all = service.getAllTreatments();
			return new ResponseDTO(1, "Success", all);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	// Add New Treatment
	@PostMapping
	public ResponseDTO saveTreatment(@RequestBody TreatmentDTO treatmentDTO) {

		try {
			TreatmentDTO response = service.addTreatment(treatmentDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	// Update Treatment By ID
	@PutMapping("/{id}")
	public ResponseDTO updateTreatment(@PathVariable("id") int id, @RequestBody TreatmentDTO treatmentDTO) {

		try {
			TreatmentDTO response = service.updateTreatment(id, treatmentDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {

			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	// Delete Treatment By ID
	@DeleteMapping("/{id}")
	public ResponseDTO deleteTreatment(@PathVariable("id") int id) {

		try {
			if (service.deleteTreatment(id)) {
				return new ResponseDTO(1, "Successfully Deleted Treatment", "");
			} else {
				return new ResponseDTO(1, "No Such Treatment", "");
			}
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), "");
		}

	}

	// Find Treatment By ID
	@GetMapping("/{id}")
	public ResponseDTO findTreatmentById(@PathVariable("id") int id) {

		try {
			TreatmentDTO treatmentById = service.getTreatmentById(id);
			if (treatmentById != null) {
				return new ResponseDTO(1, "Success", treatmentById);
			} else {
				return new ResponseDTO(1, "No User", null);
			}
		} catch (Exception ex) {
			return new ResponseDTO(0, "No User", null);
		}

	}

}
