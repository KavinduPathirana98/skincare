package com.skincare.api.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skincare.api.DTO.PatientDTO;
import com.skincare.api.Entity.Patient;
import com.skincare.api.IRepository.IPatientRepository;
import com.skincare.api.IService.IPatientService;
@Service
public class PatientService implements IPatientService {

	@Autowired
	IPatientRepository repo;

	// Get All Patients
	@Override
	public List<PatientDTO> getAllPatients() {
		try {
			List<PatientDTO> response = new ArrayList<>();

			List<Patient> dataSet = repo.findAll();
			dataSet.forEach(patient -> {
				response.add(new PatientDTO(patient.getId(), patient.getfName(), patient.getlName(), patient.getNIC(),
						patient.getEmail(), patient.getPhone()));
			});
			return response;
		} catch (Exception ex) {
			throw ex;
			
		}

	}

	// Get Patient By ID
	@Override
	public PatientDTO getPatientById(int id) {
		try {
			Patient patient = repo.findById(id).get();

			PatientDTO patientDTO = new PatientDTO();
			BeanUtils.copyProperties(patient, patientDTO);

			return patientDTO;
		} catch (Exception ex) {
			throw ex;
		}

	}
	//Delete Patient By ID
	@Override
	public boolean deletePatient(int id) {

		try {
			repo.delete(repo.findById(id).get());
			return true;
		} catch (NoSuchElementException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
	}
	//Update Patient By ID
	@Override
	public PatientDTO updatePatient(int id, PatientDTO patientDTO) {

		try {
			Patient patient = repo.findById(id).get();

			if (patient != null) {
				patientDTO.setId(id);
				return addPatient(patientDTO);
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	//Add New Patient
	@Override
	public PatientDTO addPatient(PatientDTO patientDTO) {
		try {
			
			 	Patient patient = new Patient();
			 	PatientDTO returnPatientDTO = new PatientDTO();
		        BeanUtils.copyProperties(patientDTO, patient);
		        Patient savedPatient = repo.save(patient);
		        BeanUtils.copyProperties(savedPatient, returnPatientDTO);
		        return returnPatientDTO;

		} catch (Exception ex) {
			throw ex;
		}
	}
}
