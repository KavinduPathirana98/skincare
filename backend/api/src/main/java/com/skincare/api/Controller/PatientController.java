package com.skincare.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.DTO.ResponseDTO;
import com.skincare.api.IService.IPatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	IPatientService service;

	//Get All Patients
	@GetMapping
	public ResponseDTO getAllPatients() {

		try {
			List<PatientDTO> allPatients = service.getAllPatients();
			return new ResponseDTO(1, "Success", allPatients);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Add New Patient
	@PostMapping
	public ResponseDTO savePatient(@RequestBody PatientDTO patientDTO) {

		try {
			PatientDTO response = service.addPatient(patientDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {
			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Update Patient By ID
	@PutMapping("/{id}")
	public ResponseDTO updatePatient(@PathVariable("id") int id, @RequestBody PatientDTO patientDTO) {

		try {
			PatientDTO response = service.updatePatient(id, patientDTO);
			return new ResponseDTO(1, "Success", response);
		} catch (Exception ex) {

			return new ResponseDTO(0, ex.getMessage().toString(), null);
		}

	}

	//Delete Patient By ID
	@DeleteMapping("/{id}")
	public ResponseDTO deletePatient(@PathVariable("id") int id) {

		try {
			if (service.deletePatient(id)) {
				return new ResponseDTO(1, "Successfully Deleted Patient", "");
			} else {
				return new ResponseDTO(1, "No Such Patient", "");
			}
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, ex.getMessage().toString(), "");
		}


	}

	//Find User By ID
	@GetMapping("/{id}")
	public ResponseDTO findUserById(@PathVariable("id") int id) {

		try {
			PatientDTO patientById = service.getPatientById(id);
			if (patientById != null) {
				return new ResponseDTO(1, "Success", patientById);
			} else {
				return new ResponseDTO(1, "No User",null);
			}	
		}
		catch(Exception ex)
		{
			return new ResponseDTO(0, "No User", null);
		}
		
	}

}
